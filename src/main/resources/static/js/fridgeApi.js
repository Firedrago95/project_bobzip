$(document).ready(function() {

    loadFridgeIngredients();
});

function loadFridgeIngredients() {
    $.ajax({
        url: "/fridge/all",
        method: "GET",
        success: function(response) {
            response.ingredientNames.forEach(appendIngredient)
        },
        error: function(xhr) {
            if (xhr.status == 401) {
                const currentUrl = window.location.href;
                alert(xhr.responseText);
                window.location.href = "/members/login?redirectURL=" + encodeURIComponent(currentUrl);
            } else {
                alert("댓글 작성중 문제가 발생했습니다.")
            }
        }
    });
}

function appendIngredient(ingredient) {
    const ingredientList = $('#ingredientList');

    const listItem = $('<div></div>')
        .addClass('ingredient-item col-2')
        .text(ingredient);

    const inputForm = $('<input type="hidden" name="ingredeints[]" value="'+ingredient+'">');

    const deleteButton = $('<button></button>')
        .text('x')
        .addClass('btn btn-outline-danger btn-sm')
        .css({
            'margin-left': '.5rem',
            '--bs-btn-padding-y': '.1rem',
            '--bs-btn-padding-x': '.4rem',
            '--bs-btn-font-size': '.8rem'
        })
        .click( function() {
            $(this).parent().remove();
        });

    listItem.append(deleteButton);
    listItem.append(inputForm);
    ingredientList.append(listItem);
}