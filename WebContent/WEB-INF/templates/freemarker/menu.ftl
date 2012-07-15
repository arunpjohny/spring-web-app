<#import "/WEB-INF/templates/freemarker/macro.ftl" as macro/>

<div id="nav-bar" class="navbar navbar-fixed-top no-print" data-spy="scroll" data-target=".navbar-inner">
	<div class="navbar-inner container">
		<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</a>

		<div class="nav-collapse">
			<@macro.navigation ulclass="nav" />
		</div>

		<#if _user?exists>
		<div class="nav-collapse">
			<ul class="pull-right nav">
				<li><a href="#">${_user.username}</a></li>
				<li><a href="${rc.getContextPath()}/j_spring_security_logout">Sign Out</a></li>
			</ul>
		</div>
		</#if>
	</div><!-- navbar-inner -->
</div><!-- navbar -->
