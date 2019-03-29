<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><@spring.message "page.survey.survey-field" /></title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel='stylesheet'href='https://fonts.googleapis.com/css?family=Arimo'>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css">

</head>

<body style="font-family: 'Arimo'">
    <div class="container-fluid">

    <!-- Add Menu -->
    <#include "/menubar.ftl">

        <form action="#" method="post" class="card" style="margin: 15px;">
            <div class="card-header text-white" style="background-color: #004900">
                <h2 class="card-title" style="display: inline">Amostras MIP Coletadas para <strong>${mipPestSurvey.surveyField.field.farmer.name}</strong> em <strong>${mipPestSurvey.surveyField.harvest.name}</strong> </h2>
            </div>

            <div class="card-body table-responsive-md">

                <!-- Add Success Message -->
                <#if success>
                    <#include "/success-msg.ftl">                    
                </#if>            

                <table id="mainTable" class="table table-striped table-hover display nowrap">
                    <thead style="background-color: #004900; color: white">
                        <tr>
                            <th class="col-sm-3"><@spring.message "table.list.colletion-date" /></th>
                            <th class="col-sm-3"><@spring.message "table.list.days-after-emergence" /></th>
                            <th class="col-sm-2"><@spring.message "table.list.defoliation-rate" /></th>                            
                            <th class="col-sm-2"><@spring.message "table.list.growth-phase" /></th>
                            <th class="col-sm-2"><@spring.message "table.list.action" /></th>    
                        </tr>
                    </thead>
                    <tbody id="mainTable-body">

                        <#list samplePests as samplePest>

                            <tr>
                                <td>${samplePest.sampleDate?string.medium}</td>
                                <td>${samplePest.daysAfterEmergence}</td>
                                <td>${samplePest.defoliation}</td>
                                <td>${samplePest.growthPhase}</td>
                                <td>
                                    <#assign detailsLabel><@spring.message "table.list.see-detail" /></#assign>

                                    <a href="#">
                                        <i class="material-icons" style="color: #004900" title="${detailsLabel}">details</i>                                    

                                    <#assign deleteLabel><@spring.message "table.list.remove-sample" /></#assign>

                                    <a href="#deleteModal" class="text-danger" data-toggle="modal" data-id="${samplePest.id}" data-name="${samplePest.sampleDate}">
                                        <i class="material-icons" data-toggle="tooltip" title="${deleteLabel}">clear</i>
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

    <!-- Delete Modal HTML -->
    <#include "delete-modal.ftl">

    <!-- External JS libs -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>    
    <script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>    
    <script src="/js/table-config.js"></script>
    <script src="/js/survey/survey-management.js"></script>
</body>

</html>