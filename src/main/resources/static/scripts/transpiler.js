const langMap = {
    "python": "Python",
    "java": "Java",
    "cpp": "C++",
    "c": "C",
    "javascript": "JavaScript"
};

document.getElementById("transpileForm").addEventListener("submit", async function (e) {
    e.preventDefault();
    const form = e.target;

    // Set hidden input values correctly
    document.getElementById('codeHiddenTranspile').value = editor.getValue();
    document.getElementById('languageHiddenTranspile').value = document.getElementById("languageSelect").value;
    document.getElementById('targetHiddenTranspile').value = document.getElementById("targetLanguageSelect").value;
	const targetLang=document.getElementById('targetHiddenTranspile').value

    // Now use the correct IDs for formData
    const formData = new URLSearchParams();
    formData.append("code", document.getElementById("codeHiddenTranspile").value);
    formData.append("languageSelect", document.getElementById("languageHiddenTranspile").value);
    formData.append("targetLanguageSelect", document.getElementById("targetHiddenTranspile").value);

    const outputTerminal = document.getElementById('outputTerminal');
    outputTerminal.classList.add('loading');
    outputTerminal.innerHTML = `<span class="loader"></span>Transpiling your code...`;

    try {
        const response = await fetch(form.action, {
            method: form.method,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData.toString()
        });

        const result = await response.json();

 
		outputTerminal.innerHTML = `<pre><code class="language-${langMap[targetLang]}">${result.output}</code></pre>`;

		const codeBlock = outputTerminal.querySelector('code');
		if (codeBlock) {
		    hljs.highlightElement(codeBlock);
		}

    } catch (error) {
        console.error("Error:", error);
        document.getElementById('outputTerminal').innerText = "Error executing code.";
    }
});
