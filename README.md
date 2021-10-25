# DOCUMENTATION

## Overview
Here you get to know about a `custom javascript library` which enhances your programming experience and provides you with some interesting features and components.

## Importance
The main benefit of using this library is avoiding redundancy during programming.  
The instruction for using provided features of the library is shown below:-

* [Ajax calls](#ajax-calls)  
   * [Place a call](#place-a-call)
   
* [Accordion Panel](#accordion-panel)  
    * [use of Accordion Panel](#use-of-accordion-panel)
* [Modal](#modal)
   * [use of Modal](#use-of-modal)
* [Validation](#validation)  
   * [Use of validation](#use-of-validation)

## AJAX calls 
***
Here, we provide functional support for `AJAX` call in which user pass necessary information by wrapping in `JSON` format.  
information include : url,methodeType,success and failure.  
`url` -> URL for which we want to place an ajax call.   
`methodType` -> give the information about request type (default it is of GET type).   
`success` -> function execute on successful completion of ajax call.   
`failure` -> function execute on failure of ajax call.
***
### Place a call
  ```c
$$$.ajax({
"url": "requestUrl",
"methodType": "GET",
"success": function(responseData){

},
"failure": function(){

}
});
  ```
## Accordion Panel
***
To use the accordion panel we need to create a division in which we have to set property `'accordian=true'`, then inside that division, we have to create a heading and below the heading, we have to define respective information whatever we want in the new division just next to it.
***
### Use of Accordion Panel
  ```c
  <div accordian='true'>
<h1>Heading 1</h1> 
<div>
</div>

<h1>Heading 2</h1> 
<div>
</div>

<h1>Heading 3</h1> 
<div>
</div>

</div>
  ```

## Modal
***
For creating modal first we need to define some basic properties while creating modal division and also define some pre-required functions.  
following are the properties which required: -
1. division `id`
2. `forModal`='true'
3. `style`
4. `size` (optional)
5. `header` (heading)
6. `footer`
7. `maskColor` (optional)
8. `modalBackgroundColor` (optional)
9. `closeButton` (optional, already set true).

following are the pre-required functions (as property) required:-
1. `beforeOpening` :- the function will execute before opening the modal.
2. `afterOpening` :- the function will execute after opening the modal.
3. `beforeClosing` :- the function will execute before closing the modal.
4. `afterClosing` :- the function will execute after closing the modal.

Note :- for more clarification go for an explanation of how to use modal.
***
### Use of Modal
  ```c
//user written functions
//javascript part

<script>

function abBeforeOpening()
{
alert('ab before opening get called');
return true;
}

function abOpened()
{
alert('Modal with ab opened');
}

function abBeforeClosing()
{
alert('ab before closing get called');
return true;
} 

function abClosed()
{
alert("Modal with ab closed");
}

function createModal()
{
$$$.modals.show("ab");
}
</script>

// html part

<button onCLick='createModal()'>Show First Modal</button>

<div id='ab' style='display:none' forModal='true' size="400X300" header="Some header" footer="Some footer"
 maskColor="#3355ff" modalBackgroundColor="#549933" closeButton="true" beforeOpening="abBeforeOpening()" 
 afterOpening="abOpened()" beforeClosing="abBeforeClosing()" afterClosing="abClosed()">

//content for modal

</div> 
 ```
## Validation
***
In case of validation, we provide a function that takes basic validation information in JSON format and check all the validation as per user requirements.   
Note :- validation is applicable on text,textarea,select-one,instanceof RadioNodeList,checkbox.   
(we can also add any other input format as per user requirement)
***
### Use of validation
  ```c
//validation information code by user
<script>
function validate()
{
    //here, nm is name property of respective field (same in all other)
return $$$.validate("formId").isValid({
"nm":{
"required":true,
"max-length":20,
"error-pane":"nmErrorSection",
"error":{
"required": "Name required",
"max-length": "Name can not exceed 20 characters"
}
},
"ad":{
"required":true,
"error-pane": "adErrorSection",
"error":{
"required": "Address required"
}
},
"ct":{
"invalid": -1,
"error-pane": "ctErrorSection",
"error":{
"invalid": "Select city"
}
},
"gender":{
"required": true,
"error-pane":"genderErrorSection",
"error":{
"required": "Select gender"
}
},
"ag":{
"required-state": true,
"display-alert": true,
"error":{
"required-state": "Select I agree checkbox"
}
}
});
}
</script>
//html code by user

<form id='formId' action='whatever' onsubmit='return validate()'>
Name <input type='text' id='name' name='nm' >
<span id='nmErrorSection'></span><br>
Adress <textarea id='ad' name='ad'></textarea>
<span id='adErrorSection'></span><br>
Select City
<select id='ct' name='ct'>
<option value='-1'>Select City</option>
<option value='1'>Manawar</option>
<option value='2'>Ujjain</option>
<option value='3'>Dewas</option>
<option value='4'>Indore</option>
</select>
<span id='ctErrorSection'></span><br>
Gender &nbsp;&nbsp;&nbsp;&nbsp;
Male <input type='radio' name='gender' id='ml' value='M'>
&nbsp;&nbsp;&nbsp;
Female <input type='radio' name='gender' id='fe' value'F'>
&nbsp;&nbsp;&nbsp;
<span id='genderErrorSection'></span><br>
I Agree<input type='checkbox' name='ag' id='ag' value='Y'><br>
<Button type='submit'>Register</button>      
</form>

  ```
  ***
  NOTE :- You can also go with example for more clarification.