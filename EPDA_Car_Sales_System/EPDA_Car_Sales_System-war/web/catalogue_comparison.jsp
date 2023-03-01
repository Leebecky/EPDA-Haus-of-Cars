<%-- 
    Document   : home
    Created on : 02-Feb-2023, 17:10:25
    Author     : leebe
--%>

<%@page import="model.MstMember"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    MstMember user = (session.getAttribute("user") != null) ? (MstMember) session.getAttribute("user") : new MstMember();
    String username = user.getUsername();
    System.out.println(user);
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Haus of Cars</title>
        <jsp:include page="html_head.jspf"/>
    </head>
    <body>
        <jsp:include page="_layout/header.jsp"/>

        <h1>Comparison page Design</h1>
        <div class="container-fluid d-flex justify-content-center">
            <div class="row mt-5">
                <div class="col-sm-4">
                    <div class="card">
                        <img src="https://imgur.com/edOjtEC.png" class="card-img-top" width="100%">
                        <div class="card-body pt-0 px-0">
                            <div class="d-flex flex-row justify-content-between mb-0 px-3">
                                <small class="text-muted mt-1">STARTING AT</small>
                                <h6>&dollar;22,495&ast;</h6>
                            </div>
                            <hr class="mt-2 mx-3">
                            <div class="d-flex flex-row justify-content-between px-3 pb-4">
                                <div class="d-flex flex-column"><span class="text-muted">Fuel Efficiency</span><small class="text-muted">L/100KM&ast;</small></div>
                                <div class="d-flex flex-column"><h5 class="mb-0">8.5/7.1</h5><small class="text-muted text-right">(city/Hwy)</small></div>
                            </div>
                            <div class="d-flex flex-row justify-content-between p-3 mid">
                                <div class="d-flex flex-column"><small class="text-muted mb-1">ENGINE</small><div class="d-flex flex-row"><img src="https://imgur.com/iPtsG7I.png" width="35px" height="25px"><div class="d-flex flex-column ml-1"><small class="ghj">1.4L MultiAir</small><small class="ghj">16V I-4 Turbo</small></div></div></div>
                                <div class="d-flex flex-column"><small class="text-muted mb-2">HORSEPOWER</small><div class="d-flex flex-row"><img src="https://imgur.com/J11mEBq.png"><h6 class="ml-1">135 hp&ast;</h6></div></div>
                            </div>
                            <small class="text-muted key pl-3">Standard key Features</small>
                            <div class="mx-3 mt-3 mb-2"><button type="button" class="btn btn-danger btn-block"><small>BUILD & PRICE</small></button></div>
                            <small class="d-flex justify-content-center text-muted">*Legal Disclaimer</small>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="card">
                        <img src="https://imgur.com/SKZolYE.png" class="card-img-top" width="100%">
                        <div class="card-body pt-0 px-0">
                            <div class="d-flex flex-row justify-content-between mb-0 px-3">
                                <small class="text-muted mt-1">STARTING AT</small>
                                <h6>&dollar;22,495&ast;</h6>
                            </div>
                            <hr class="mt-2 mx-3">
                            <div class="d-flex flex-row justify-content-between px-3 pb-4">
                                <div class="d-flex flex-column"><span class="text-muted">Fuel Efficiency</span><small class="text-muted">L/100KM&ast;</small></div>
                                <div class="d-flex flex-column"><h5 class="mb-0">8.5/7.1</h5><small class="text-muted text-right">(city/Hwy)</small></div>
                            </div>
                            <div class="d-flex flex-row justify-content-between p-3 mid">
                                <div class="d-flex flex-column"><small class="text-muted mb-1">ENGINE</small><div class="d-flex flex-row"><img src="https://imgur.com/iPtsG7I.png" width="35px" height="25px"><div class="d-flex flex-column ml-1"><small class="ghj">1.4L MultiAir</small><small class="ghj">16V I-4 Turbo</small></div></div></div>
                                <div class="d-flex flex-column"><small class="text-muted mb-2">HORSEPOWER</small><div class="d-flex flex-row"><img src="https://imgur.com/J11mEBq.png"><h6 class="ml-1">135 hp&ast;</h6></div></div>
                            </div>
                            <small class="text-muted key pl-3">Standard key Features</small>
                            <div class="mx-3 mt-3 mb-2"><button type="button" class="btn btn-danger btn-block"><small>BUILD & PRICE</small></button></div>
                            <small class="d-flex justify-content-center text-muted">*Legal Disclaimer</small>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="card">
                        <img src="https://imgur.com/gUQNKPd.png" class="card-img-top" width="100%">
                        <div class="card-body pt-0 px-0">
                            <div class="d-flex flex-row justify-content-between mb-0 px-3">
                                <small class="text-muted mt-1">STARTING AT</small>
                                <h6>&dollar;22,495&ast;</h6>
                            </div>
                            <hr class="mt-2 mx-3">
                            <div class="d-flex flex-row justify-content-between px-3 pb-4">
                                <div class="d-flex flex-column"><span class="text-muted">Fuel Efficiency</span><small class="text-muted">L/100KM&ast;</small></div>
                                <div class="d-flex flex-column"><h5 class="mb-0">8.5/7.1</h5><small class="text-muted text-right">(city/Hwy)</small></div>
                            </div>
                            <div class="d-flex flex-row justify-content-between p-3 mid">
                                <div class="d-flex flex-column"><small class="text-muted mb-1">ENGINE</small><div class="d-flex flex-row"><img src="https://imgur.com/iPtsG7I.png" width="35px" height="25px"><div class="d-flex flex-column ml-1"><small class="ghj">1.4L MultiAir</small><small class="ghj">16V I-4 Turbo</small></div></div></div>
                                <div class="d-flex flex-column"><small class="text-muted mb-2">HORSEPOWER</small><div class="d-flex flex-row"><img src="https://imgur.com/J11mEBq.png"><h6 class="ml-1">135 hp&ast;</h6></div></div>
                            </div>
                            <small class="text-muted key pl-3">Standard key Features</small>
                            <div class="mx-3 mt-3 mb-2"><button type="button" class="btn btn-danger btn-block"><small>BUILD & PRICE</small></button></div>
                            <small class="d-flex justify-content-center text-muted">*Legal Disclaimer</small>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </body>
</html>
