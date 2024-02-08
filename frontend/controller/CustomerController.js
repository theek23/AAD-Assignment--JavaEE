import {customer} from "../model/Customer.js";

let cusId = $("#customer-id");
let cusName = $("#cus-name");
let cusPhoneNo = $("#contact-no")
let cusAddress = $("#address");
let birthday = $("#birthday");

loadAllCus();
$(document).ready(function(){
    // Attach click event listener to the Save button
    $('.customer-btns button').eq(0).click(function() {
        saveCustomer();
    });
});
function saveCustomer() {

    let newCustomer = Object.assign({}, customer);
    newCustomer.cusID = cusId.val();
    newCustomer.cusName = cusName.val();
    newCustomer.cusPhoneNo = cusPhoneNo.val();
    newCustomer.cusAddress = cusAddress.val();
    newCustomer.birthday = birthday.val();

    $.ajax({
        url:"http://localhost:8080/pos/customer",
        method: "POST",
        data:JSON.stringify(newCustomer),
        contentType:"application/json",
        success:function (res) {
            console.log(res);
            alert("Customer Added Successfully");
            loadAllCus();
        },
        error:function (ob, textStatus, error) {
            alert(textStatus+" : Error Customer Not Added")
            console.log(error)
        }
    });
}
function loadAllCus() {
    $("#customerTable").empty();
    $.ajax({
        url: "http://localhost:8080/pos/customer?option=GET",
        method: "GET",
        success: function (res) {
            console.log(res);
            for (var r of res) {
                // Adjusted to match the table structure and columns
                let row = `
                  <thead>
                  <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Contact No</th>
                    <th>Address</th>
                    <th>Birthday</th>
                  </tr>
                  </thead>
                    <tr>
                     <td>${r.cusID}</td>
                     <td>${r.cusName}</td>
                     <td>${r.phNo}</td> 
                     <td>${r.address}</td>
                     <td>${r.birthday}</td> 
                    </tr>`;
                $("#customerTable").append(row);
                //bindTrrEvents();
            }
        }
    });
}
