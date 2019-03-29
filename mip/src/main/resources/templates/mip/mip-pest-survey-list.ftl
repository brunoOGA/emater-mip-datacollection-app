<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><@spring.message "page.pest.survey-list" /></title>

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
                <h2><@spring.message "card.title.survey-list" /></h2>
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
                        <#list mipPestSurveys as mipPestSurvey>
                            <tr>
                                <td>${mipPestSurvey.surveyField.harvest.name}</td>
                                <td>${mipPestSurvey.surveyField.field.name}</td>
                                <td>${mipPestSurvey.surveyField.field.location}</td>
                                <td>${mipPestSurvey.surveyField.field.city.name}</td>
                                <td>${mipPestSurvey.surveyField.seedName}</td>
                                <td>${mipPestSurvey.surveyField.field.farmer.name}</td>
                                <td>
                                    <#list mipPestSurvey.surveyField.field.supervisors as supervisor>
                                        <span>${supervisor.name}</span> <br>
                                    </#list>
							    </td>
                                <td>
                                    <#assign colectionLabel><@spring.message "modal.button.collect" /></#assign> 

                                    <a href="/pest-survey/add-sample?mipPestSurveyId=${mipPestSurvey.id}">
                                        <i class="material-icons" style="color: #004900" title="${colectionLabel}">add_circle</i>

                                    <#assign seeListLabel><@spring.message "modal.button.seeList" /></#assign> 

                                    <a href="/mip/sample/pest/list?mipPestSurveyId=${mipPestSurvey.id}">
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