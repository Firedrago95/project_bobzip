$(document).ready(function() {
    $('#addIngredientBtn').click(addIngredient);

    function addIngredient() {
        const ingredientInput = $('#ingredientInput');
        const ingredient = ingredientInput.val().trim();

        if (ingredient) {
            const ingredientList = $('#ingredientList');

            const listItem = $('<div></div>')
                .addClass('ingredient-item col-2')
                .text(ingredient);

            const inputForm = $('<input type="hidden" name="ingredients[]" value="'+ ingredient +'">');

            const deleteButton = $('<button></button>')
                .text('x')
                .addClass('btn btn-outline-danger btn-sm')
                .css({
                    'margin-left': '.5rem',
                    '--bs-btn-padding-y': '.1rem',
                    '--bs-btn-padding-x': '.4rem',
                    '--bs-btn-font-size': '.8rem'
                })
                .click(function() {
                    $(this).parent().remove();
                });

            listItem.append(deleteButton);
            listItem.append(inputForm);
            ingredientList.append(listItem);
            ingredientInput.val('');
        }
    }
});