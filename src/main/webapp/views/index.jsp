<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Code Compiler</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.10/codemirror.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.10/theme/eclipse.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.10/theme/material.min.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Fira+Code&display=swap"
	rel="stylesheet">


<link rel="stylesheet" href="/css/style.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.13/codemirror.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.13/mode/python/python.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.13/mode/clike/clike.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.10/addon/edit/matchbrackets.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.10/addon/edit/closebrackets.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/lang-detector@1.0.6/lang-detector.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/python.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/java.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/cpp.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/javascript.min.js"></script>


</head>
<body>
	<h1>Code Compiler</h1>
	<nav>
  <a href="/">Compiler</a> |
  <a href="/transpile">Transpiler</a>
</nav>

	<div class="container">
		<div id="controls">

			<div class="language-controls">
				<label for="languageSelect">Current:</label> <select
					id="languageSelect" >
					 <option value="java">Java</option>
  <option value="python3">Python 3</option>
  <option value="cpp">C++</option>
  <option value="c">C</option>
  <option value="nodejs">Node.js</option>
				</select>
			</div>
			
			

		</div>
		<div id="form">
			<div class="controls">
				<form id="compileForm" action="/api/compile" method="post"
					style="display: inline;">
					<input type="hidden" id="codeHidden" name="code" /> <input
						type="hidden" id="languageSelectHidden" name="languageSelect" />
					<input type="hidden" id="codeInputHidden" name="input" />

					<button type="submit">Compile</button>
				</form>
			</div>




			<div id="formatContainers">
				<div id="editorContainer">
					<div id="editor">
						<textarea id="code" cols="40">// write your code here add even the libraries</textarea>
					</div>
				</div>
				<div id="outputContainer">
					<div id="outputTerminal">// output will show here</div>
				</div>
			</div>
			<div id="inputContainer">
				<label for="inputText">Custom Input:</label><br>
				<textarea id="inputText" rows="10" cols="50"
					placeholder="Enter input here (if your code uses scanf, cin, or input())"></textarea>
			</div>
			<div id="uploadControls">
			<label for="fileInput">Upload File:</label>
				<input type="file" id="fileInput" accept=".txt,.java,.py,.cpp,.c,.js" />
			</div>
		</div>
	</div>

	<script src="/scripts/index.js"></script>
	<script src="/scripts/common.js"></script>

</body>
</html>