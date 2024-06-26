function readURL(input, targetId) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById(targetId).src = e.target.result;
        }
        reader.readAsDataURL(input.files[0]);
    }
}

function addIngredient(containerId) {
    const container = document.getElementById(containerId);
    const newForm = container.querySelector(".ingredientForm").cloneNode(true);
    newForm.querySelectorAll("input").forEach(input => input.value = "");
    container.appendChild(newForm);
}

function deleteIngredient(button, containerId) {
    const container = document.getElementById(containerId);
    if (container.querySelectorAll(".ingredientForm").length > 1) {
        button.parentElement.parentElement.remove();
    }
}

function addRecipeStep(containerId) {
    const container = document.getElementById(containerId);
    const newForm = container.querySelector(".cookingStepForm").cloneNode(true);

    newForm.querySelectorAll("input, textarea").forEach(input => {
        input.value = "";
        input.name = "";
    });
    newForm.querySelectorAll("img").forEach(input => {
            input.src = "/image/noImage.png";
    });

    container.appendChild(newForm);

    updateStepNumbers(containerId);
}

function deleteRecipeStep(button, containerId) {
    const container = document.getElementById(containerId);
    if (container.querySelectorAll(".cookingStepForm").length > 1) {
        button.parentElement.parentElement.remove();
        updateStepNumbers(containerId);
    }
}

function updateStepNumbers(containerId) {
    const container = document.getElementById(containerId);
    const steps = container.querySelectorAll(".cookingStepForm");

    steps.forEach((step, index) => {
        step.querySelector(".step-number").value = index + 1;
        step.querySelector(".step-number").name = `steps[${index}].stepNumber`;

        step.querySelector("textarea").name = `steps[${index}].instruction`;

        const fileInput = step.querySelector("input[type=file]");
        fileInput.name = `steps[${index}].thumbnail`;
        fileInput.setAttribute("onchange", `readURL(this, 'step${index}')`);

        step.querySelector(".img-thumbnail").id = `step${index}`;
    });
}

document.addEventListener('DOMContentLoaded', () => {
    updateStepNumbers('cooking_step');
});