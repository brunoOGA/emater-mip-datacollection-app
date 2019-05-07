<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><@spring.message "title.macro-region"/></title>

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
                <h2 class="card-title" style="display: inline"><@spring.message "title.macro-region"/></h2>

                <a href="/base/macro-region/new" class="btn btn-success float-right">
                    <i class="material-icons align-middle">&#xE147;</i>
                    <span class="align-middle"><@spring.message "button.new.macro-region"/></span>
                </a>                
            </div>

            <div class="card-body table-responsive-md">

                <!-- Add Success Message -->
                <#if success??>
                    <#include "/success-msg.ftl">   

                <#elseif fail??>
                    <#include "/fail-msg.ftl">   
       
                </#if>
                

                <#include "main-table.ftl">

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