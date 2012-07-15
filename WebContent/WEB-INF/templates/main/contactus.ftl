<#import "/WEB-INF/templates/freemarker/macro.ftl" as macro/>

<@macro.header />
	<div class="row-fluid" id="app-contactus">
		<div class="span9">
			<form action="" method="POST" class="form-horizontal well">
				<fieldset>
					<@macro.ctrltext name="name" label="Name" class="" size="span9"/>
					<@macro.ctrltext name="mobile" label="Mobile" class="" size="span9"/>
					<@macro.ctrltext name="from" label="Email" class="" size="span9"/>
					<@macro.ctrltext name="subject" label="Subject" class="" size="span9"/>
					<@macro.ctrltextarea name="body" label="Body" class="height-large" size="span9"/>
				</fieldset>
				
				<div class="form-actions">
					<span class="send-mail btn btn-primary">Send Mail</span>
					<span class="cancel-mail btn">Cancel</span>
				</div>
			</form>
		</div> <!-- span9 -->
		<div class="span3">
			<address>
				<header><h4>Address</h4></header>
				<div>Address1</div>
				<div>Address2</div>
			</address>
		</div>
	</div><!-- row -->
<@macro.footer>
	<script language="" src="${rc.getContextPath()}/resources/js/main/contactus.js"></script>
	<script>
		$(function(){
			new app.main.contactus.Main("#app-contactus");
		});
	</script>
</@macro.footer>