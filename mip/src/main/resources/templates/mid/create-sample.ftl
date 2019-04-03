<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><@spring.message "page.pest.sample" /></title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="/css/smart_wizard.css" type="text/css" />
    <link rel="stylesheet" href="/css/smart_wizard_theme_arrows.css" type="text/css" />
    <link rel='stylesheet'href='https://fonts.googleapis.com/css?family=Arimo'>

</head>

<body style="font-family: 'Arimo'">
    <div class="container-fluid">

    <!-- Add Menu -->
    <#include "/menubar.ftl">

        <form action="/pest-survey/save-sample" method="post" class="card" style="margin: 15px">

            <#--  <input type="hidden" name="mipPestSurveyId" value="${mipPestSurveyId}">  -->

            <div class="card-header text-white" style="background-color: #004900">
                <h2 class="card-title" style="display: inline"><@spring.message "card.title.sample" /></h2>
            </div>

            <div id="smartwizard" class="card-body">

                <ul>
                    <li>
                        <a href="#collector-and-sample-date-tab"><@spring.message "table.list.dates" /></a>
                    </li>
                    <li>
                        <a href="#spore-tab"><@spring.message "table.list.spore-collector-tab" /></a>
                    </li>
                    <li>
                        <a href="#inspection-tab"><@spring.message "table.list.inspection" /></a>
                    </li>
                    <li>
                        <a href="#fungicide-tab"><@spring.message "table.list.fungicide" /></a>
                    </li>

                </ul>

                <div class="tab-content">
                    <div id="collector-and-sample-date-tab" class="card" style="margin-top: 15px">
                        <div class="card-header text-white" style="background-color: #004900">
                            <@spring.message "table.list.dates" />
                        </div>
                        <div class="card-body">
                            <div class="form-row">
                                <div class="form-group col">
                                    <label for="collector-install-date"><@spring.message "table.list.collector-install-date" /></label>
                                    <input type="date" class="form-control" id="collector-install-date" name="collector-install-date" required>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col">
                                    <label for="colletion-date"><@spring.message "table.list.colletion-date" /></label>
                                    <input type="date" class="form-control" id="colletion-date" name="colletion-date" required>
                                </div>
                            </div>

                            <div class="form-check col">
                                <input class="form-check-input" type="checkbox" id="sporeCollector" name="sporeCollector">
                                <label class="form-check-label" for="sporeCollector">
                                    <@spring.message "table.list.spore-collector" />
                                </label>
                            </div>
                                
                        </div>
                    </div>

                    <div id="spore-tab" class="card" style="margin-top: 15px">
                        <div class="card-header text-white" style="background-color: #004900">
                            <@spring.message "table.list.spore-collector-tab" />
                        </div>
                        <div class="card-body">
        
                            <div class="form-row">
                                <div class="form-check col">
                                    <input class="form-check-input" type="checkbox" id="blade-installed-pre-cold" name="blade-installed-pre-cold">
                                    <label class="form-check-label" for="blade-installed-pre-cold">
                                        <@spring.message "table.list.blade-installed-pre-cold" />
                                    </label>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="reading-blade-responsible-name"><@spring.message "table.list.reading-blade-responsible-name" /></label>
                                    <input type="text" class="form-control" id="reading-blade-responsible-name" name="reading-blade-responsible-name" required maxlength="50" >
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="reading-blade-responsible-name-entity"><@spring.message "table.list.reading-blade-responsible-name-entity" /></label>
                                    <input type="text" class="form-control" id="reading-blade-responsible-name-entity" name="reading-blade-responsible-name-entity" required maxlength="50" >
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col">
                                    <label for="blade-reading-date"><@spring.message "table.list.blade-reading-date" /></label>
                                    <input type="date" class="form-control" id="blade-reading-date" name="blade-reading-date" required>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col">
                                    <label for="blade-reading-rust-result"><@spring.message "table.list.blade-reading-rust-result" /> </label>
                                    <input type="number" step="1" class="form-control" id="blade-reading-rust-result" name="blade-reading-rust-result">
                                </div>                                
                            </div>
                        </div>
                    </div>

                    <div id="inspection-tab" class="card" style="margin-top: 15px">
                        <div class="card-header text-white" style="background-color: #004900">
                            <@spring.message "table.list.sample-pest-population" />
                        </div>
                        <div class="card-body table-responsive-md">
                            <table id="mainTable" class="table table-striped table-hover">
                                <thead style="background-color: #004900; color: white">
                                    <tr>
                                        <th><@spring.message "table.list.pest-name" /></th>
                                        <th><@spring.message "table.list.average-found" /></th>
                                    </tr>
                                </thead>
                                <tbody id="mainTable-body">
                                    <#--  <#list pestList as pest>
                                        <tr>
                                            <td>${pest.usualName} - ${pest.scientificName} (${pest.pestSize.getName()})</td>
                                            <td><input class="form-control" id="${pest.id}" name="${'pest_'+pest.id}" type="text" placeholder="0.0"></td>
                                        </tr>                                    
                                    </#list>  -->
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div id="fungicide-tab" class="card" style="margin-top: 15px">
                        <div class="card-header text-white" style="background-color: #004900">
                            <@spring.message "table.list.sample-pest-population" />
                        </div>
                        <div class="card-body table-responsive-md">
                            <table id="mainTable" class="table table-striped table-hover">
                                <thead style="background-color: #004900; color: white">
                                    <tr>
                                        <th><@spring.message "table.list.pest-name" /></th>
                                        <th><@spring.message "table.list.average-found" /></th>
                                    </tr>
                                </thead>
                                <tbody id="mainTable-body">
                                    <#--  <#list pestList as pest>
                                        <tr>
                                            <td>${pest.usualName} - ${pest.scientificName} (${pest.pestSize.getName()})</td>
                                            <td><input class="form-control" id="${pest.id}" name="${'pest_'+pest.id}" type="text" placeholder="0.0"></td>
                                        </tr>                                    
                                    </#list>  -->
                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>
            </div>

            <div class="card-footer">
                <#assign saveButton><@spring.message "modal.button.register-sample" /></#assign>
                <input style="display: none" id="saveButton" type="submit" class="btn btn-success float-right" value="${saveButton}"/>
            </div>
        </form>

    </div>

    <!-- External JS libs -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <script src="/js/jquery.smartWizard.min.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(document).ready(function () {

            // Smart Wizard
            $('#smartwizard').smartWizard({
                lang: {  // Language variables
                    next: 'Próximo',
                    previous: 'Anterior'
                },
                theme: 'arrows',
                transitionEffect: 'fade', // Effect on navigation, none/slide/fade
                transitionSpeed: '400'
            });

            // Show conclusion button only in the last step
            $("#smartwizard").on("showStep", function(e, anchorObject, stepNumber, stepDirection) {
			if($('button.sw-btn-next').hasClass('disabled')){
				$('#saveButton').show(); // show the button extra only in the last page

			}else{
				$('#saveButton').hide();				
			}
	      });

        }); 
    </script>
</body>

</html>