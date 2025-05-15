<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Code Transpiler & Compiler</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.10/codemirror.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.10/theme/eclipse.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.10/theme/material.min.css" />
<link href="https://fonts.googleapis.com/css2?family=Fira+Code&display=swap" rel="stylesheet">


    <link rel="stylesheet" href="/css/style.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.13/codemirror.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.13/mode/python/python.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.13/mode/clike/clike.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.10/addon/edit/matchbrackets.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.10/addon/edit/closebrackets.min.js"></script>


</head>
<body>
<h1>Code Transpiler & Compiler</h1>

  <form id="compileForm" action="/api/compile" method="post">
    <textarea id="code" rows="50" cols="80">// write your code here</textarea>
    <input type="hidden" id="codeHidden" name="code" />
    <button type="submit">Compile</button>
</form>

<form id="transpileForm" action="/api/transpile" method="post">
    <input type="hidden" id="codeHiddenTranspile" name="code" />
    <button type="submit">Transpile</button>
</form>

<select id="languageSelect">
    <option value="python">Python</option>
    <option value="text/x-java">Java</option>
</select>
</form>
      <script src="/scripts/index.js"></script>
</body>
</html>