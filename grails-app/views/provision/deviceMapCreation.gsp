<%--
  Created by IntelliJ IDEA.
  User: KS148
  Date: 14/03/16
  Time: 15:04
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    %{--Bootstrap stylesheet--}%
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css">
    %{--Material Design--}%
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/materialDesign/material.css">
    %{--Data Table--}%
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/dataTable/buttons.dataTables.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/dataTable/dataTables.tableTools.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/dataTable/jquery.dataTables.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/dataTable/select.dataTables.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/dataTable/stylingDataTable.css">

    %{--jquery--}%
    <script src="<%=request.getContextPath()%>/js/jquery/jquery-1.12.0.js"></script>
    %{--Bootstrap--}%
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
    %{--Material Design--}%
    <script src="<%=request.getContextPath()%>/js/materialDesign/material.js"></script>
    %{--Data Table--}%
    <script src="<%=request.getContextPath()%>/js/dataTable/jquery.dataTables.js"></script>
    <script src="<%=request.getContextPath()%>/js/dataTable/dataTables.select.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/dataTable/dataTables.buttons.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/dataTable/dataTables.tableTools.js"></script>
    %{--Form Validate--}%
    <script src="<%=request.getContextPath()%>/js/validate/jquery.form.js"></script>
    <script src="<%=request.getContextPath()%>/js/validate/jquery.validate.min.js"></script>
    <style>
        .marginTop{
            margin-top: 5%;
        }

        textarea {
            margin: 0px;
            width: 100%;
            height: 200px;
        }

        label {
            margin-bottom: 0px;
        }
    </style>
</head>

<body>
    <div class="container">

        <div class="col-md-12 col-lg-12 col-sm-12 marginTop" id="deviceMapDiv">

            %{--Device Map Table--}%
            <table id="deviceMapTable" class="display">
                <thead>
                    <tr>
                        %{-- Column Names in Header Section--}%
                        <th>Device Map</th>
                        <th>Category</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>


    <div class="modal fade" id="deviceMapCreation" tabindex="-1" role="dialog" aria-labelledby="projectLabel" >
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Device Map</h4>
                </div>
                <div class="modal-body">
                    <form id="deviceMapForm">
                        <div class="row marginTop">
                            <div class="col-md-10 col-lg-10 col-sm-12">
                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    <label class="mdl-textfield__label" for="newCategory">Select Category</label>
                                    <select class="mdl-textfield__input" id="categorySelection" name="categorySelection">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-10 col-lg-10 col-sm-12">
                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    <label class="mdl-textfield__label" for="newCategory">New Category</label>
                                    <input type="text" class="mdl-textfield__input" id="newCategory" name="newCategory">
                                </div>
                            </div>
                        </div>

                        <div class="row marginTop">
                            <div class="col-md-10 col-lg-10 col-sm-12">
                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    %{--<label for="deviceMapJSON">Create Device Map JSON</label>--}%
                                    <textarea id="deviceMapJSON" name="deviceMapJSON"></textarea>
                                    <label class="mdl-textfield__label" for="deviceMapJSON">Device Map JSON</label>
                                </div>
                            </div>
                        </div>

                        <div class="row marginTop">
                            <div class="col-md-10 col-lg-10 col-sm-12">
                                <input type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>

        /**
         * For fetching and displaying the categories
         */
        function getProjectCategory() {
            $.ajax({
                url     :   "../provision/fetchDeviceCategories",
                type    :   "GET",
                success :   function (categories) {
                            console.log("categories"  + categories);
                            $.each(categories, function (val, firmwareName) {
                                $("#categorySelection").append($('<option>', {
                                    value: firmwareName,
                                    text: firmwareName
                                }));
                            });
                },
                error   :   function () {
                            console.log("Problem");
                            return;
                }
            });
        }

        /**
         *
         */
        function validateDeviceMapForm() {
            $.validator.addMethod("jsonCheck", function (value, element) {
                try {
                    JSON.parse(value);
                    return true;
                } catch (e) {
                    return false;
                }
            }, 'Please enter correct JSON');


            $("#deviceMapForm").validate({
                rules : {
                    categorySelection   :   {required: true},
//                    newCategory       :   {required: true},
                    deviceMapJSON       :   { jsonCheck : true}
                },

                messages: {
                    categorySelection   :   "Please Select the Category",
                    deviceMapJSON       :   "Please Create Device Map"
                },

                submitHandler   :   function (form) {
                    $(form).ajaxSubmit({
                        url     :   '../provision/setDeviceMap',                           //Path of the controller action
                        type    :   'POST',
                        success :   function (response) {
                                    console.log(response)
                        },
                        error   :   function () {
                                    console.log("Error");
                        }
                    });
                    return false;
                }
            });
        }

        function showExistingDeviceMap(deviceMapList) {
            //Destroy tables previous instance of project Table
            var deviceMapTable = $("#deviceMapTable").DataTable();
            deviceMapTable.destroy();

            //Create project Data Table
            deviceMapTable = $("#deviceMapTable").DataTable({
                "bProcessing"   :   true,
                data            :   deviceMapList,
                "sPaginationType": "full_numbers",
                dom             :   'Bfrtip',
                buttons         :  [{
                                        text: 'Add New',
                                        action: function ( e, dt, node, config ) {
                                            $("#deviceMapCreation").modal('show');
                                        }
                                    },
                                    {
                                            text: 'Update',
                                            action: function ( e, dt, node, config ) {
                                                //    $("#projectModification").modal('show');
                                            }
                                    },
                                    {
                                            text: 'Delete',
                                            action: function ( e, dt, node, config ) {
                                            }
                                    }
                                    ]
                });
        }

        function getDeviceMap() {
            $.ajax({
                url     :   "../provision/fetchDeviceMap",
                type    :   "GET",
                success :   function (deviceMapList) {
                            console.log("deviceMapList : " + deviceMapList);

                            deviceMapList = [
                                    ["fggfggfdgf","gfdgdfgfdgdfg"],
                                    ["fdgdfgdfgdg","gfdgfdgdgdfg"]

                            ];
                            showExistingDeviceMap(deviceMapList)
                },
                error   :   function () {
                            console.log("Problem");
                            return;
                }
            });
        }

        $(document).ready(function(){

            getDeviceMap();
//            showExistingDeviceMap();

            getProjectCategory();

//            showExistingDeviceMap();

            validateDeviceMapForm();
        });
    </script>
</body>
</html>