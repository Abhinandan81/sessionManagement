<%--
  Created by IntelliJ IDEA.
  User: KS148
  Date: 09/03/16
  Time: 17:33
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>File Uploader</title>

        %{--Bootstrap stylesheet--}%
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css">
        %{--font Awesome--}%
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/fontAwsome/font-awesome.css">

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

        <h2>Upload Image</h2>
        <button id="importFile" class="btn btn-default btn-sm" accept="image/*"><i class="fa fa-upload" title="import"></i>
            <input  type="file" id="fileUploader" class="file-btn photoProof_Uploader" name="fileUploader" data-url='<g:createLink controller="provision" action="uploadFile"/>'  accept="image/*" multiple/>
        </button>

        <script>
            $(document).ready(function(){

                //Hide the file input button as file is getting submitted on second attempt if shown
                $('#fileUploader').hide();

                //jquery function for the file selection
                document.querySelector('#importFile').addEventListener('click', function(e) {
                    var fileInput = document.querySelector('#fileUploader');
                    fileInput.click();
                }, false);
            });
        </script>
    </body>
</html>