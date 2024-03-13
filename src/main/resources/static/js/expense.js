// hook up with add new transaction button
function deleteTransactionById(transactionId) {
    const currentUrl = window.location.href;
    const url = `${currentUrl}/${transactionId}`;
    axios.delete(url)
        .then(response => {
            if (response.status === 200) {
                window.location.reload();
            }
        }).catch(error => {
        // handle error
        console.log(error);
    });
}

// hook up with edit transaction button
function editTransactionById(transactionId) {
    console.log(axios);
    const currentUrl = window.location.href;
    const url = `${currentUrl}/${transactionId}`;
    axios.get(url)
        .then(response => {
            // handle success
            populateToEditForm(response.data);
        }).catch(error => {
        // handle error
        console.log(error);
    });
}

function populateToEditForm(transaction) {
    const {date, id, description, amount, category} = transaction;
    $("#id-edit").val(id);
    $("#date-edit").val(date);
    $("#description-edit").val(description);
    $("#category-edit").val(category.id)
    $("#amount-edit").val(amount);
}