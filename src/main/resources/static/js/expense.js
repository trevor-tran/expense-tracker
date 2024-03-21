const HOST = `${location.protocol}//${location.host}`;

// hook up with add new transaction button
function deleteTransactionById(transactionId, csrf) {
    const {token, parameterName} = csrf;
    const url = `${HOST}/transaction/${transactionId}?${parameterName}=${token}`;
    axios.delete(url)
        .then(response => {
            window.location.reload();
        }).catch(error => {
        // handle error
        console.log(error);
    });
}

// hook up with edit transaction button
function editTransactionById(transactionId, csrf) {
    const {token, parameterName} = csrf;
    const url = `${HOST}/transaction/${transactionId}?${parameterName}=${token}`;
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