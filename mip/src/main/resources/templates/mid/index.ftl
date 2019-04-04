<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><@spring.message "card.title.rust-monitoring" /></title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel='stylesheet'href='https://fonts.googleapis.com/css?family=Arimo'>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">

</head>

<body style="font-family: 'Arimo'">
    <div class="container-fluid">

    <!-- Add Menu -->
    <#include "/menubar.ftl">

        <form action="#" method="post" class="card" style="margin: 15px">
            <div class="card-header text-white" style="background-color: #004900">
                <h2><@spring.message "card.title.rust-monitoring" /></h2>
            </div>

            <div class="card-body table-responsive-md">

                <!-- Add Success Message -->
                <#if success>
                    <#include "/success-msg.ftl">                    
                </#if>                     

                <table id="mainTable" class="table table-striped table-hover">
                    <thead style="background-color: #004900; color: white">
                        <tr>
                            <th><@spring.message "table.list.harvest" /></th>
                            <th><@spring.message "table.list.identification" /></th>
                            <th><@spring.message "table.list.location" /></th>
                            <th><@spring.message "table.list.city" /></th>
                            <th><@spring.message "table.list.seed-name" /></th>
                            <th><@spring.message "table.list.farmer" /></th>
                            <th><@spring.message "table.list.supervisor" /></th>
                            <th><@spring.message "table.list.action" /></th>
                        </tr>
                    </thead>
                    <tbody id="mainTable-body">
                        <#list rustMonitoringList as rustMonitoring>
                            <tr>
                                <td>${rustMonitoring.harvestName}</td>
                                <td>${rustMonitoring.fieldName}</td>
                                <td>${rustMonitoring.fieldLocation}</td>
                                <td>${rustMonitoring.fieldCityName}</td>
                                <td>${rustMonitoring.seedName}</td>
                                <td>${rustMonitoring.farmerName}</td>
                                <td>
                                    <#list rustMonitoring.supervisorNames as supervisor>
                                        <span>${supervisor}</span> <br>
                                    </#list>
							    </td>
                                <td>
                                    <#assign colectionLabel><@spring.message "modal.button.collect" /></#assign> 

                                    <a href="/mid/rust-monitoring/create?surveyFieldId=${rustMonitoring.surveyFieldId}">
                                        <i class="material-icons" style="color: #004900" title="${colectionLabel}">add_circle</i>

                                    <#assign seeListLabel><@spring.message "modal.button.seeList" /></#assign> 

                                    <a href="/mid/rust-monitoring/sample/list?surveyFieldId=${rustMonitoring.surveyFieldId}">
                                        <i class="material-icons" style="color: #004900" title="${seeListLabel}">list</i>

                                    </a>
                                </td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            </div>

            <div class="card-footer text-muted">

            </div>
        </form>

    </div>

    <!-- External JS libs -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>    
    <script src="/js/table-config.js"></script>

</body>

</html>