var stepIndex = 1; // 과정 인덱스를 관리하기 위한 변수

function readURL(input, id) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById(id).src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    } else {
        document.getElementById(id).src = "";
    }
}

function clearInputs(element) {
    element.querySelectorAll("input, select").forEach(function(input) {
        input.value = "";
    });
    element.querySelectorAll("img").forEach(function(img) {
        img.src = "";
    });
}

function addIngredient(id) {
    const ingredientList = document.getElementById(id);
    const newIngredient = document.querySelector("#" + id + " li").cloneNode(true);
    clearInputs(newIngredient);
    ingredientList.appendChild(newIngredient);
}

function addRecipeStep(id) {
    const recipeStepList = document.getElementById(id);
    const newRecipeStep = document.querySelector("#" + id + " li").cloneNode(true);
    clearInputs(newRecipeStep);

    // 이미지 태그에 id 설정
    const stepImageId = "step" + stepIndex;
    newRecipeStep.querySelector("img").id = stepImageId;

    // 이미지 입력 태그의 onchange 이벤트에 새로운 readURL 함수 적용
    newRecipeStep.querySelector("input[type=file]").setAttribute("onchange", "readURL(this, '" + stepImageId + "')");

    stepIndex++;
    recipeStepList.appendChild(newRecipeStep);
}

function deleteForm(obj, id) {
    const list = document.getElementById(id);
    const childCount = list.querySelectorAll("li").length;

    if (childCount > 1) {
        list.removeChild(obj.parentNode);
    }
}