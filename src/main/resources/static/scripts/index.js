document.getElementById("compileForm").addEventListener("submit", async function (e) {
	    e.preventDefault();
	    const form = e.target;

	   
	    document.getElementById('codeHidden').value = editor.getValue();
	    document.getElementById('languageSelectHidden').value = document.getElementById("languageSelect").value;
	    document.getElementById('codeInputHidden').value = document.getElementById("inputText").value;


	    const formData = new URLSearchParams();
	    formData.append("code", document.getElementById("codeHidden").value);
	    formData.append("languageSelect", document.getElementById("languageSelectHidden").value);
	    formData.append("input", document.getElementById("codeInputHidden").value);
	    const outputTerminal = document.getElementById('outputTerminal');
	    outputTerminal.classList.add('loading');
	    outputTerminal.innerHTML = `<span class="loader"></span> Compiling your code...`;


	    try {
	        const response = await fetch(form.action, {
	            method: form.method,
	            headers: {
	                'Content-Type': 'application/x-www-form-urlencoded'
	            },
	            body: formData.toString()
	        });

	        const result = await response.json();
	       

	        document.getElementById('outputTerminal').innerText = result.output;
	    } catch (error) {
	        console.error("Error:", error);
	        document.getElementById('outputTerminal').innerText = "Error executing code.";
	    }
	});