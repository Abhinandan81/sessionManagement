<%--
  Created by IntelliJ IDEA.
  User: KS115
  Date: 3/11/16
  Time: 1:47 PM
--%>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Person List</title>
</head>
<body>
<div class="nav" role="navigation">
    <ul>
        <li><g:link class="create" action="upload">Upload Data</g:link></li>
    </ul>
</div>
<div id="list-person" class="content scaffold-list" role="main">
    <h1>Inventory Data</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <g:sortableColumn property="eyeManufacturingId" title="Eye Manufacturing Id" />
            <g:sortableColumn property="eyeManufactureDate" title="Eye Manufacture Date" />
            <g:sortableColumn property="eyeDescription" title="Eye Description" />
        </tr>
        </thead>
        <tbody>
        <g:each in="${inventoryList}" status="i" var="inventoryInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td><g:link action="show" id="${inventoryInstance.id}">${fieldValue(bean: inventoryInstance, field: "eyeManufacturingId")}</g:link></td>
                <td><g:formatDate date="${inventoryInstance.eyeManufactureDate}" type="date" style="LONG"/></td>
                <td>${fieldValue(bean: inventoryInstance, field: "eyeDescription")}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${inventoryTotal}" />
    </div>
</div>
</body>
</html>