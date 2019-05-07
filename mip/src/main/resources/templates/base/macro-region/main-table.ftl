<table id="mainTable" class="table table-striped table-hover">
    <thead style="background-color: #004900; color: white">
        <tr>
            <th class="col-sm-11"><@spring.message "label.macro-region.name" /></th>
            <th class="col-sm-1"><@spring.message "label.action" /></th>
        </tr>
    </thead>
    <tbody id="mainTable-body">
        <#list macroregions as macroregion>
            <tr>
                <td>${macroregion.name}</td>
                <td>
                    <#assign updateLabel><@spring.message "label.update" /></#assign>
                    <#assign deleteLabel><@spring.message "label.delete" /></#assign>

                    <a href="#" class="text-warning">
                        <i class="material-icons" data-toggle="tooltip" title="${updateLabel}">&#xE254;</i>
                    </a>
                    <a href="#" class="text-danger">
                        <i class="material-icons" data-toggle="tooltip" title="${deleteLabel}">&#xE872;</i>
                    </a>
                </td>
            </tr>
        </#list>
    </tbody>
</table>
