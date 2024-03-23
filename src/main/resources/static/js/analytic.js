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
        const dataPoint = {label: v.titleCase(categoryName), data: []};

        for (const d of chartData.labels) {
            let amountPerDate = 0;
            transArr.forEach(v => {
                if (v.date === d) {
                    amountPerDate += Number(v.amount);
                }
            });

            dataPoint.data.push(amountPerDate);
        }
        dataPoint.backgroundColor = COLORS[colorIndex];
        dataPoint.fill = true;
        colorIndex++;

        chartData.datasets.push(dataPoint);
    });
    return chartData;
}

function loadChart(transactions) {
    const currentMonthTransactions = getCurrentMonthTransactions(transactions);
    const lineChartData = transformToBarChartData(transactions);

    const barChartData = transformToBarChartData(currentMonthTransactions);
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

    const barChartCtx = $("#barChart");
    const doughnutChartCtx = $("#doughnutChart");
    const lineChartCtx = $("#lineChart");

    // bar chart
    new Chart(barChartCtx, {
        type: 'bar',
        data: barChartData,
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Expenses By Day'
                },
                tooltip: {
                    callbacks: {
                        title: tooltipItems => dayjs(tooltipItems[0].label).format('MMM. DD YYYY')
                    }
                },
                legend: {
                    position: 'top',
                    labels: {
                        font: {
                            family: "sans-serif",
                            size: 14,
                            weight: "bolder",
                        },
                    }
                }
            },
            scales: {
                x: {
                    stacked: true,
                    ticks: {
                        callback: function (value, index, ticks) {
                            return dayjs(this.getLabelForValue(value)).format('MMM. DD YYYY')
                        }
                    }
                },
                y: {
                    stacked: true,
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'USD ($)'
                    }
                }
            }
        }
    });

    // doughnut chart
    new Chart(doughnutChartCtx, {
        type: 'doughnut',
        data: doughnutChartData,
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Expenses By Category'
                },
                legend: {
                    position: 'top',
                    labels: {
                        // This more specific font property overrides the global property
                        font: {
                            family: "sans-serif",
                            size: 14,
                            weight: "bolder"
                        }
                    }
                }
            }
        },
        plugins: [{
            beforeDraw: function (chart) {
                // resource: https://github.com/chartjs/Chart.js/discussions/10077
                const ctx = chart.ctx;
                const {width, height, top} = chart.chartArea;
                const centerX = width / 2;
                const centerY = height / 2 + top;

                ctx.restore();

                const metrics = ctx.measureText('100.0%');
                ctx.clearRect(centerX - metrics.width / 2, centerY - 30, metrics.width, 60);
                ctx.textAlign = "center";

                ctx.textBaseline = "bottom";
                ctx.font = "0.7em sans-serif";
                ctx.fillText("Total Expenses", centerX, centerY);

                // Format to USD using the locale, style, and currency.
                let USDollar = new Intl.NumberFormat('en-US', {
                    style: 'currency',
                    currency: 'USD',
                });

                const totalSpending = chart.config.data.datasets[0].data.reduce((acc, curr) => acc + curr, 0);
                ctx.textBaseline = "top";
                ctx.font = "bolder 1.3em sans-serif";
                ctx.fillText(USDollar.format(totalSpending), centerX, centerY);

                ctx.save();
            }
        }]
    });

    // line chart
    new Chart(lineChartCtx, {
        type: 'line',
        data: lineChartData,
        options: {
            responsive: true,
            interaction: {
                intersect: false,
                mode: 'index',
            },
            plugins: {
                title: {
                    display: true,
                    text: 'Expense Trends'
                },
                tooltip: {
                    callbacks: {
                        title: tooltipItems => dayjs(tooltipItems[0].label).format('MMM. DD YYYY'),
                        footer: tooltipItems => {
                            let total = 0;

                            tooltipItems.forEach(function(tooltipItem) {
                                total += tooltipItem.parsed.y;
                            });
                            return 'Total: ' + total;
                        }
                    }
                },
                legend: {
                    position: 'top',
                    labels: {
                        font: {
                            family: "sans-serif",
                            size: 14,
                            weight: "bolder",
                        },
                    }
                }
            },
            scales: {
                x: {
                    ticks: {
                        callback: function (value, index, ticks) {
                            return dayjs(this.getLabelForValue(value)).format('MMM. DD YYYY')
                        }
                    }
                },
                y: {
                    stacked: true,
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'USD ($)'
                    }
                }
            },
            elements: {
                point:{
                    radius: 0
                }
            }
        }
    });
}

function getCurrentMonthTransactions(transactions) {
    const startOfCurrentMonth = dayjs().startOf('M');
    const endOfCurrentMonth = dayjs().endOf('M');
    return transactions.filter(t =>
        dayjs(t.date).isSameOrAfter(startOfCurrentMonth) &&
        dayjs(t.date).isSameOrBefore(endOfCurrentMonth)
    );
}
