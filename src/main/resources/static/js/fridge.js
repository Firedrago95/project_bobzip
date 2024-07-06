$(document).ready(function() {
    $('#addIngredientBtn').click(addIngredient);
    $('#searchRecipesBtn').click(searchRecipes);

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

    function searchRecipes() {
        const ingredients = [];
        $('#ingredientList .ingredient-item').each(function() {
            ingredients.push($(this).clone().children().remove().end().text().trim());
        });

        // 예시 레시피 데이터 (실제 구현에서는 API 호출 등을 사용)
        const exampleRecipes = [
            { name: 'Pasta', ingredients: ['pasta', 'tomato', 'cheese'] },
            { name: 'Salad', ingredients: ['lettuce', 'tomato', 'cucumber'] },
            { name: 'Sandwich', ingredients: ['bread', 'lettuce', 'cheese'] },
        ];

        const recipeList = $('#recipeList');
        recipeList.empty();

        exampleRecipes.forEach(recipe => {
            if (ingredients.every(ingredient => recipe.ingredients.includes(ingredient))) {
                const card = $('<div></div>').addClass('card recipe-card');
                const cardBody = $('<div></div>').addClass('card-body');

                const cardTitle = $('<h5></h5>').addClass('card-title').text(recipe.name);
                cardBody.append(cardTitle);

                const ingredientList = $('<ul></ul>').addClass('list-group list-group-flush');
                recipe.ingredients.forEach(ingredient => {
                    const ingredientItem = $('<li></li>').addClass('list-group-item').text(ingredient);
                    ingredientList.append(ingredientItem);
                });
                cardBody.append(ingredientList);

                card.append(cardBody);
                recipeList.append(card);
            }
        });
    }
});