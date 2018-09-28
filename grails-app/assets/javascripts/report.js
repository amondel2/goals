$(document).ready(function(){
   $("#gobtn").on('click',function(){
       window.open(window.fmBaseDir + 'generateReport?year=' + $("#year_year").val(), "reportPSSHEET");
   });

   $("#gobtnemp").on('click',function(){
       window.open(window.fmBaseDir + 'generateEmpReport?year=' + $("#ryear_year").val(), "reportEMPPSSHEET");
   });

});