val auxilDir = "auxil" // the directory where the auxilary files will be located from the root of the project
val outDir = "out" // the directory where the final pdf will be located from the root of the project
val srcDir = "src" // the directory where the <name>.tex and <name>.bib file is located from the root of the project
val mainTexFile = "main" // without the .tex extension

tasks.register("bibtex") {
    outputs.upToDateWhen { false }
    description =
        "Compiles the bibliography after the first pass of pdflatex"
    dependsOn("pdflatex-A")
    exec {
        setWorkingDir("${project.rootDir}/$srcDir")
        commandLine(
            "bibtex",
            "${project.rootDir}/$auxilDir/$mainTexFile",
        )
    }
}

tasks.register("build-mid-step") {
    outputs.upToDateWhen { false }
    dependsOn("bibtex")
    finalizedBy("pdflatex-B")
}

tasks.register("build") {
    outputs.upToDateWhen { false }
    description = "Build the document, including the bibliography and resolving references"
    dependsOn("build-mid-step")
    finalizedBy("pdflatex-C")
}

fun configurePdflatexTask(taskName: String) {
    tasks.register(taskName) {
        outputs.upToDateWhen { false }
        description = "Compiles the latex document without reusing cached files"
        exec {
            setWorkingDir("${project.rootDir}/$srcDir")
            commandLine(
                "pdflatex",
                "-file-line-error",
                "-interaction=nonstopmode",
                "-synctex=1",
                "-output-format=pdf",
                "-output-directory=${project.rootDir}/$outDir",
                "-aux-directory=${project.rootDir}/$auxilDir",
                mainTexFile,
            )
            // needed to ignore the exit value of pdflatex, since it returns 1 for warnings also
            isIgnoreExitValue = true
        }
    }
}

// pdflatex writes information about the bibliography style and .bib file, as well as
// all occurrences of \cite{...}, to the file main.aux, assuming the main tex file is main.tex
configurePdflatexTask("pdflatex-A")

// When pdflatex is run again, it now sees that a main.bbl file is available! So it inserts the contents
// of main.bbl.
// After this step, the reference list appears in the output PDF formatted according to the chosen \bibliographystyle{...},
// but the in-text citations are still [?].
configurePdflatexTask("pdflatex-B")

// the \cite{...} commands are replaced with the corresponding numerical labels in the output PDF
configurePdflatexTask("pdflatex-C")

// more information at: https://www.overleaf.com/learn/latex/Bibliography_management_with_bibtex#Enter_\(\mathrm{Bib\TeX}\)
