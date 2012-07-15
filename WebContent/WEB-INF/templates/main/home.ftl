<#import "/WEB-INF/templates/freemarker/macro.ftl" as macro/>

<@macro.header />
	<div id="app-home">
		
	</div> <!-- app-home -->
<@macro.footer>
	<script language="" src="${rc.getContextPath()}/resources/js/main/home.js"></script>
	<script>
		$(function(){
			new app.main.home.Main("#app-home");
		});
	</script>
</@macro.footer>