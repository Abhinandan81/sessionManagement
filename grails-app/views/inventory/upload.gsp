<%--
  Created by IntelliJ IDEA.
  User: KS115
  Date: 3/10/16
  Time: 10:50 AM
--%>

<!DOCTYPE html>
<html>
<head>
    %{--<meta name="layout" content="main">--}%
    <title>Upload</title>

    %{--jquery--}%
    <script src="<%=request.getContextPath()%>/js/jquery/jquery-1.12.0.js"></script>

    %{--File Upload plugins--}%
    <script src="<%=request.getContextPath()%>/js/file_uplaod/jquery.ui.widget.js"></script>
    <script src="<%=request.getContextPath()%>/js/file_uplaod/jquery.fileupload.js"></script>
    <script src="<%=request.getContextPath()%>/js/file_uplaod/jquery.fileupload-process.js"></script>
    <script src="<%=request.getContextPath()%>/js/file_uplaod/jquery.fileupload-validate.js"></script>

    %{--Custom js for file upload validation--}%
    <script src="<%=request.getContextPath()%>/js/file_uplaod/uploadFunction.js"></script>
</head>

<body>
<h1>Upload Data</h1>
<button id="importFile" class="btn btn-default btn-sm" accept="application/vnd.ms-excel"><i class="fa fa-upload" title="import"></i>

            <input  type="file" id="fileUploader" class="file-button photoProof_Uploader" name="fileUploader" data-url='<g:createLink controller="inventory" action="doUpload"/>'  accept="application/vnd.ms-excel" multiple/>

       </button>

<script>

</script>
</body>
</html>