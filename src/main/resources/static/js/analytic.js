const sample = [
    {
        "id": "6ce04cf2-fae0-4a4e-bd49-8b0a2a68d39",
        "date": "2024-03-01",
        "description": "2121",
        "amount": 20,
        "userId": null,
        "category": {
            "id": 1,
            "name": "restaurant"
        }
    },
    {
        "id": "6ce04cf2-fae0-4a4e-bd49-8b0a5a68df9",
        "date": "2024-03-01",
        "description": "2121",
        "amount": 20,
        "userId": null,
        "category": {
            "id": 1,
            "name": "restaurant"
        }
    },
    {
        "id": "6ce04cf2-fae0-4a4e-bd49-8b0a2a68df39",
        "date": "2024-03-05",
        "description": "2121",
        "amount": 10,
        "userId": null,
        "category": {
            "id": 1,
            "name": "restaurant"
        }
    },
    {
        "id": "a72494e3-7b06-4d2d-9ff6-19b520ce9532",
        "date": "2024-03-05",
        "description": "fds",
        "amount": 10,
        "userId": null,
        "category": {
            "id": 2,
            "name": "shopping"
        }
    }
]

function convertToChartData(transactions) {
    const dateSet = new Set();
    const categoryToTrans = new Map();

    for (const trans of transactions) {
        const {date, amount, category} = trans;
        dateSet.add(date);

        if (!categoryToTrans.has(category.name)) {
            categoryToTrans.set(category.name, []);
        }
        categoryToTrans.get(category.name).push({date, amount});
    }
        console.log(categoryToTrans)

    const chartData = {labels: [], datasets: []};
    chartData.labels = Array.from(dateSet).sort();

    categoryToTrans.forEach((transArr, categoryName) => {
        const dataPoint = {label: categoryName, data: []};

        for (const d of chartData.labels) {
            let amountPerDate = 0;
            transArr.forEach(v => {
                if (v.date === d) {
                    amountPerDate += Number(v.amount);
                }
            });

            dataPoint.data.push(amountPerDate);
        }

        chartData.datasets.push(dataPoint);
    });
    console.log(chartData);
    return chartData;
}

function loadChart(transactions) {
    const chartData = convertToChartData(transactions);
    const ctx = $("#myChart");

    new Chart(ctx, {
        type: 'bar',
        data: chartData,
        options: {
            responsive: true,
            scales: {
                x: {
                    stacked: true,
                },
                y: {
                    stacked: true,
                    beginAtZero: true
                }
            }
        }
    });
}
