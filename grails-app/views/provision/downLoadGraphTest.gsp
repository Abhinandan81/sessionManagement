<%--
  Created by IntelliJ IDEA.
  User: KS148
  Date: 11/03/16
  Time: 09:31
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Chart Download</title>

    %{--Bootstrap stylesheet--}%
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css">
    %{--font Awesome--}%
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/fontAwsome/font-awesome.css">

    <script src="<%=request.getContextPath()%>/js/jquery/jquery-1.12.0.js"></script>
    %{--Chart js --}%
    <script src="<%=request.getContextPath()%>/js/chartJS/Chart.js"></script>
    %{--For generating legends for chart js--}%
    <script src="<%=request.getContextPath()%>/js/chartJS/legend.js"></script>

    <style>
        .dropbtn {
        background-color: #737373;
        color: white;
        padding: 16px;
        font-size: 16px;
        border: none;
        cursor: pointer;
        }

        .dropbtn:hover, .dropbtn:focus {
        background-color: #4CAF50;
        }

        .dropdown {
        position: relative;
        display: inline-block;
        }

        .dropdown-content {
        display: none;
        position: absolute;
        background-color: #f9f9f9;
        min-width: 160px;
        overflow: auto;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
        }

        .dropdown-content a {
        color: black;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
        }

        .dropdown a:hover {background-color: #f1f1f1}

        .show {display:block;}
    </style>

</head>

<body>
    <div class="dropdown">
        <button onclick="myFunction()" class="dropbtn"><i class="fa fa-download" title="download"></i></button>
        <div id="myDropdown" class="dropdown-content">
            <a class="downLoad" href="#">gif</a>
            <a class="downLoad" href="#">jpeg</a>
            <a class="downLoad" href="#">png</a>
        </div>
    </div>


    <div class="">
        <canvas id="dougnutChart" width="400" height="400"></canvas>
        <div id="doughnutLegends"></div>
    </div>

    <script>
        function downLoadChart(format) {
            $.ajax({
                url     :   "<%=request.getContextPath()%>/provision/downLoadChart",
                type    :   "POST",
                data    :   {"chartId" : "dougnutChart", "outputFormat": format, "path": "provision/downLoadGraphTest" },
                success :   function(response) {
                            console.log("DownLoaded");
                },
                error   :   function () {
                            console.log("Problem Occured");
                }
            });
        }

        /* When the user clicks on the button,
         toggle between hiding and showing the dropdown content */
        function myFunction() {
            document.getElementById("myDropdown").classList.toggle("show");
        }

        $(document).ready(function () {

            var dataForDoughnutChart = [
                {
                    value: 300,
                    color:"#F7464A",
                    highlight: "#FF5A5E",
                    label: "Red"
                },
                {
                    value: 50,
                    color: "#46BFBD",
                    highlight: "#5AD3D1",
                    label: "Green"
                },
                {
                    value: 100,
                    color: "#FDB45C",
                    highlight: "#FFC870",
                    label: "Yellow"
                }
            ];

            var ctx = $("#dougnutChart").get(0).getContext("2d");
            var myPieChart = new Chart(ctx).Doughnut(dataForDoughnutChart);

//            $("#doughnutLegends").innerHTML = myPieChart.generateLegend();
            legend(document.getElementById('doughnutLegends'), dataForDoughnutChart);


            $('.downLoad').click(function(){
                downLoadChart((this).innerHTML);
            });

            // Close the dropdown if the user clicks outside of it
            window.onclick = function(event) {
                if (!event.target.matches('.dropbtn')) {

                    var dropdowns = document.getElementsByClassName("dropdown-content");
                    var i;
                    for (i = 0; i < dropdowns.length; i++) {
                        var openDropdown = dropdowns[i];
                        if (openDropdown.classList.contains('show')) {
                            openDropdown.classList.remove('show');
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>