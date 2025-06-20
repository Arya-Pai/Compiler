const codeTextarea = document.getElementById('code');
const MIN_ROWS = 30;
const MAX_ROWS=70;
const LINE_HEIGHT = 30;

const modeMap = {
    "python": "python",
    "java": "text/x-java",
    "cpp": "text/x-c++src",
    "c": "text/x-csrc",
    "javascript": "javascript"
};

const nameMap = {
    "python": "Python",
    "java": "Java",
    "cpp": "C++",
    "c": "C",
    "javascript": "JavaScript"
};

var editor = CodeMirror.fromTextArea(codeTextarea, {
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
    const clampedLines = Math.max(Math.max(lineCount, MIN_ROWS), MAX_ROWS);
    const newHeight = clampedLines * LINE_HEIGHT;
    editor.setSize(null, newHeight + "rem");
}

function detectAndSetLanguage() {
	const langSelect=document.getElementById("languageSelect");
    const code = editor.getValue();
    const result = hljs.highlightAuto(code, Object.keys(modeMap));
    const detected = result.language;

    if (detected && modeMap[detected]) {
        const cmMode = modeMap[detected];
        editor.setOption("mode", cmMode);
		langSelect.disabled=true;
        // Update UI labels
       //document.getElementById("languageSelect").innerText = `Current Language: ${nameMap[detected]}`;

        // Optional: update dropdown
       
        if (langSelect) langSelect.value = cmMode;
		langSelect.disabled=false;
    
        const targetSelect = document.getElementById("targetLanguageSelect");
        const transpileBtn = document.getElementById("transpileBtn");
        if (targetSelect && transpileBtn) {
            transpileBtn.diabled = (cmMode === targetSelect.value);
			console.log(cmMode === targetSelect.value);
			console.log("1"+targetSelect && transpileBtn);
        }
    }
}

editor.on("change", () => {
    autoResizeEditor(editor);
    detectAndSetLanguage();
});
autoResizeEditor(editor);
detectAndSetLanguage();


document.getElementById("compileForm").addEventListener("submit", function () {
	document.getElementById('codeHidden').value = editor.getValue();
	    document.getElementById('languageSelectHidden').value = document.getElementById("languageSelect").value;
	    document.getElementById('codeInputHidden').value = document.getElementById("inputText").value;
});


