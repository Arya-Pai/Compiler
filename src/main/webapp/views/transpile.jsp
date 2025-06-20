<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Code Transpiler</title>
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
	<h1>Code Transpiler</h1>
	
	<nav>
  <a href="/">Compiler</a> |
  <a href="/transpile">Transpiler</a>
</nav>
	

	<div class="container">
		<div id="controls_t">

			<div class="language-controls">
				<label for="languageSelect">Current:</label> <select
					id="languageSelect" disabled>
					<option value="python">Python</option>
					<option value="text/x-java">Java</option>
					<option value="text/x-c++src">C++</option>
					<option value="text/x-csrc">C</option>
					<option value="javascript">JavaScript</option>
				</select>
			</div>
			<div class="language-controls">
				<label for="targetLanguageSelect">Target:</label> <select
					id="targetLanguageSelect">
					<option value="python">Python</option>
					<option value="text/x-java">Java</option>
					<option value="text/x-csrc">C</option>
					<option value="text/x-c++src">C++</option>
					<option value="javascript">JavaScript</option>
				</select>
			</div>

		</div>
		<div id="form">
			<div class="controls_t">
				<div class="controls_t">
					<form id="transpileForm" action="/api/transpile" method="post"
						style="display: inline;">
						<input type="hidden" id="codeHiddenTranspile" name="code" />
						<button type="submit" id="transpileBtn">Transpile</button>
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
			</div>
		</div>
		
</div>

	<script src="/scripts/index.js"></script>

</body>
</html>