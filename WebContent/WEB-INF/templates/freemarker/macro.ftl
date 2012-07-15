<#macro header title="" metadescription="" metakeywords="">
<#if _isAjaxRequest = false>
	<html>
		<head>
			<meta charset="utf-8">
		
			<title>Spring Web Application</title>

			<meta name="domain" content="www.arunpjohny.com">
			<meta name="author" content="Arun P Johny">
			<meta name="copyright" content="July 2012">
			<meta name="contact" content="mail@mail.com">

			<meta name="description" content="This is an sample web application using spring and hibernate">

			<meta http-equiv="X-UA-Compatible" content="IE=edge">
		
			<!-- Mobile viewport optimized: j.mp/bplateviewport -->
			<meta name="viewport" content="width=device-width,initial-scale=1">
	
			<#include "header-resources.ftl" />
		</head>
		<body>
	
			<#include "menu.ftl" />
			
			<div class="app-header container">
				<div class="app-logo pull-left">
					<img src="${rc.getContextPath()}/resources/images/logo.gif" height="65px"></img>
				</div>
				<div class="app-contact pull-right text-right">
					<div>
						<div class="phone">
							<h4>Call us: 000-0000000</h4>
						</div>
						<h5 class="email"><a href="#mailto:mail@mail.com">mail@mail.com</a></h5>
					</div>
					<div class="social-connect">
						<a href="http://www.facebook.com" target="_blank"><div class="connect-facebook"></div></a>
						<a href="https://twitter.com/" target="_blank"><div class="connect-twitter"></div></a>
					</div>
				</div>
			</div>
			<div class="container">
				<div class="app-content">
					<div>
						<#include "header.ftl" />
					</div>
					<div id="page-container">
</#if>
</#macro>

<#macro footer>
<#if _isAjaxRequest = false>
					</div><!-- #page-container -->
					<div>
						<#include "footer.ftl" />
					</div>
				</div><!-- app-content -->
			</div><!-- container -->
	
			<#include "footer-resources.ftl" />
			
			<#nested>
		</body>
	</html>
<#else>
	<#nested>
</#if>
</#macro>

<#macro title title>
	<div class="page-title">
		<h3 class="bordered-light-b title">${title}</h3>
	</div>
</#macro>

<#macro ctrlfile name label class="" size="" errorplacement="inline" value="">
	<div class="control-group">
		<label class="control-label">${messageResolver.getMessage( label )}</label>
		<div class="controls">
			<input type="file" name="${name}" class="${size} ${class}" error-placement="${errorplacement}" all-true="true" value="${value!""}"/>
		</div>
	</div>
</#macro>
<#macro ctrltextarea name label value="" class="" size="" errorplacement="inline">
	<div class="control-group">
		<label class="control-label">${messageResolver.getMessage( label )}</label>
		<div class="controls">
			<textarea name="${name}" class="${size} ${class}" error-placement="${errorplacement}" all-true="true">${value}</textarea>
		</div>
	</div>
</#macro>
<#macro ctrltext name label class="" size="" errorplacement="inline" value="" placeholder="" hidden=false>
	<div class="control-group <#if hidden==true>hide</#if>">
		<label class="control-label">${messageResolver.getMessage( label )}</label>
		<div class="controls">
			<input type="text" name="${name}" class="${class} ${size}" error-placement="${errorplacement}" value="${value!""}" placeholder="${placeholder}"/>
		</div>
	</div>
</#macro>
<#macro ctrlreadonly name label class="" size="" errorplacement="inline" value="">
	<div class="control-group">
		<label class="control-label">${messageResolver.getMessage( label )}</label>
		<div class="controls">
			<input type="text" name="${name}" class="uneditable-input ${class} ${size}" error-placement="${errorplacement}" readonly="readonly" value="${value!""}"/>
		</div>
	</div>
</#macro>
<#macro ctrlcheckbox name label value=false class="" errorplacement="inline">
	<div class="control-group app-ctrl-chkbx">
		<div class="controls">
			<input type="checkbox" name="${name}" class="pull-left ${class}" error-placement="${errorplacement}" <#if value == true>checked="checked"</#if>/>
			<label class="pull-left">
				${messageResolver.getMessage( label )}
			</label>
		</div>
	</div>
</#macro>
<#macro ctrlpassword name label class="" size="" errorplacement="inline" value="" placeholder="">
	<div class="control-group">
		<label class="control-label">${messageResolver.getMessage( label )}</label>
		<div class="controls">
			<input type="password" name="${name}" class="${class} ${size}" error-placement="${errorplacement}" value="${value!""}" placeholder="${placeholder}"/>
		</div>
	</div>
</#macro>
<#macro ctrlcombo label class="" size="" errorplacement="inline" placeholder="">
	<div class="control-group">
		<label class="control-label">${messageResolver.getMessage( label )}</label>
		<div class="controls">
			<div class="${class}" data-combosize="${size}" error-placement="${errorplacement}" placeholder="${placeholder}"></div>
		</div>
	</div>
</#macro>


<#macro navigation ulclass="">
		<ul class="app-navigation nav">
			<li>
				<a class="nav-ajax" href="${rc.getContextPath()}/home">Home</a>
			</li>
			<li>
				<a class="nav-ajax" href="${rc.getContextPath()}/contactus">Contact Us</a>
			</li>
		</ul>
</#macro>

<#macro sidebar>
			<div class="contact-sidebar">
					<#nested>
			</div><!-- contact-sidebar -->
</#macro>

<#macro contactsidebar>
			<div class="contact-sidebar">
				<@morelinkspanle>
					<#nested>
				</@morelinkspanle>				
				<@getintouchpanel />
			</div><!-- contact-sidebar -->
</#macro>

<#macro morelinkspanle title="More Links">
				<#assign links><#nested></#assign>
				<#if links?has_content>
					<section class="section more-links">
						<header class="type2"><h4>${title}</h4></header>
						<article>
							<#nested>
						</article>
					</section>
				</#if>
</#macro>
