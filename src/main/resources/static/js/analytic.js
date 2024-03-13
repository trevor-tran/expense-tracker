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
const COLORS = [
    '#4BC0C0',
    '#FF6384',
    '#FF9F40',
    '#9966FF',
    '#FFCD56',
    '#36A2EB',
    '#C9CBCF',
    '#FF6384',
    '#537bc4',
    '#166a8f',
    '#58595b',
];

function transformToBarChartData(transactions) {
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

    const chartData = {labels: [], datasets: []};
    chartData.labels = Array.from(dateSet).sort();

    let colorIndex = 0;
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
        dataPoint.backgroundColor = COLORS[colorIndex++];

        chartData.datasets.push(dataPoint);
    });
    return chartData;
}

function loadChart(transactions) {
    const barChartData = transformToBarChartData(transactions);
    const doughnutChartData = {
        labels: [],
        datasets: [{
            label: "",
            data: [],
            backgroundColor: COLORS
        }]
    };

    barChartData.datasets.forEach(v => {
        const {label, data} = v;
        doughnutChartData.labels.push(label);
        const sumPerCategory = data.reduce((acc, current) => acc + current, 0);
        doughnutChartData.datasets[0].data.push(sumPerCategory);
    })

    console.log(doughnutChartData)

    const barChartCtx = $("#barChart");
    const doughnutChartCtx = $("#doughnutChart");

    new Chart(barChartCtx, {
        type: 'bar',
        data: barChartData,
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Current Month Expenses'
                }
            },
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

    new Chart(doughnutChartCtx, {
        type: 'doughnut',
        data: doughnutChartData,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Current Month Expenses'
                }
            }
        }
    });
}
