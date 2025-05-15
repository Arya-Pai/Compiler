const codeTextarea = document.getElementById('code');
const MIN_ROWS = 30;
const MAX_ROWS = 70;
const LINE_HEIGHT = 20;

var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
    lineNumbers: true,
    mode: "python",  
    theme: "material",
    indentUnit: 4,
    tabSize: 4,
    indentWithTabs: true,
    matchBrackets: true,
    autoCloseBrackets: true,
	lineWrapping: true
});

function autoResizeEditor(editor) {
    const lineCount = editor.lineCount();
    const clampedLines = Math.min(Math.max(lineCount, MIN_ROWS), MAX_ROWS);
    const newHeight = clampedLines * LINE_HEIGHT;
    editor.setSize(null, newHeight + "px");
}
editor.on("change", () => autoResizeEditor(editor));
autoResizeEditor(editor);

document.getElementById("languageSelect").addEventListener("change", function () {
    const selectedLang = this.value;
    editor.setOption("mode", selectedLang);
});

document.getElementById("compileForm").addEventListener("submit", function () {
    document.getElementById('codeHidden').value = editor.getValue();
});

document.getElementById("transpileForm").addEventListener("submit", function () {
    document.getElementById('codeHiddenTranspile').value = editor.getValue();
});

