	<div id="select-survey-field-modal" class="modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
					<div class="modal-header">						
						<h4 class="modal-title"><@spring.message "modal.title.select.harvest" /></h4>
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body table-responsive-md">					
 
						<table id="mainTable" class="table datatable table-striped table-hover">
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
					<div class="modal-footer">
						<#assign buttonSelect><@spring.message "modal.button.select" /></#assign>
						<#assign buttonCancel><@spring.message "modal.button.cancel" /></#assign>

						<input type="button" class="btn btn-default" data-dismiss="modal" value="${buttonCancel}">
						<input type="submit" class="btn btn-success" value="${buttonSelect}">
					</div>
			</div>
		</div>
	</div>	