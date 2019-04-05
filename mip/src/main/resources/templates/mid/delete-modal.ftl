	<div id="deleteModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="${urlDelete}" method="post">
					<div class="modal-header">						
						<h4 class="modal-title"><@spring.message "modal.title.delete.pest.sample" /></h4>
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">					
						<p><@spring.message "modal.message.delete.pest.sample" /> "<span id="edit-name" class="bg-danger text-white"></span>"</p>
						<p class="text-danger"><@spring.message "modal.message.delete.warning.norollback" /></p>
					</div>
					<div class="modal-footer">
						<#assign buttonCancel><@spring.message "modal.button.cancel" /></#assign>
						<#assign buttonDelete><@spring.message "modal.button.delete" /></#assign>

						<input type="hidden" class="form-control" id="edit-id" name="surveyFieldId">
						<input type="button" class="btn btn-default" data-dismiss="modal" value="${buttonCancel}">
						<input type="submit" class="btn btn-danger" value="${buttonDelete}">
					</div>
				</form>
			</div>
		</div>
	</div>
