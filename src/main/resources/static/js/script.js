// Function to create a bar chart
function createBarChart(csvData) {

    const xAxis = document.getElementById("xAxis").value;
    const yAxis = document.getElementById("yAxis").value;

// csvData to create the chart
    const data = csvData.map(row => ({
        x: +row[xAxis],
        y: +row[yAxis]

    }));
    var graphContainer = d3.select(".graph");

    var margin = { top: 10, right: 10, bottom: 20, left: 40 },
        width = graphContainer.node().getBoundingClientRect().width - margin.left - margin.right,
        height = graphContainer.node().getBoundingClientRect().height - margin.top - margin.bottom;


    var svg = graphContainer.append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    svg.append("text")
        .attr("transform", "translate(100,0)")
        .attr("x", 50)
        .attr("y", 50)
        .attr("font-size", "24px")
        .text(yAxis + " over " + xAxis);

    var xScale = d3.scaleBand().range([0, width]).padding(0.4),
        yScale = d3.scaleLinear().range([height, 0]);

    var g = svg.append("g")
        .attr("transform", "translate(" + 50 + "," + 0 + ")");

// Update the domain of xScale and yScale based on your data
    xScale.domain(data.map(function(d) { return d.x; }));
    yScale.domain([0, d3.max(data, function(d) { return d.y; })]);


    svg.append("g")
        .attr("transform", "translate(0," + height + ")")
        .call(d3.axisBottom(xScale))
        .append("text")
        .attr("y", height - 300)    // maybe delete
        .attr("x", width - 100) //maybe delete
        .attr("text-anchor", "end")
        .attr("stroke", "black")
        .text(xAxis);



    svg.append("g")
        .call(d3.axisLeft(yScale).tickFormat(function(d) {
            return d;
        }).ticks(10))
        .append("text")
        .attr("transform", "rotate(-90)")
        .attr("y", 6)
        .attr("dy", "-5.1em")
        .attr("text-anchor", "end")
        .attr("stroke", "black")
        .text(yAxis);

    svg.selectAll(".bar")
        .data(data)
        .enter().append("rect")
        .attr("class", "bar")
        .on("mouseover", onMouseOver) //Add listener for the mouseover event
        .on("mouseout", onMouseOut)   //Add listener for the mouseout event
        .attr("x", function(d) { return xScale(d.x); })
        .attr("y", function(d) { return yScale(d.y); })
        .attr("width", xScale.bandwidth())
        .transition()
        .ease(d3.easeLinear)
        .duration(400)
        .delay(function (d, i) {
            return i * 50;
        })
        .attr("height", function(d) { return height - yScale(d.y); });

    function onMouseOver(d, i) {
        d3.select(this).attr('class', 'highlight');
        d3.select(this)
            .transition()
            .duration(400)
            .attr('width', xScale.bandwidth() + 5)
            .attr("y", function(d) { return yScale(d.y) - 10; })
            .attr("height", function(d) { return height - yScale(d.y) + 10; });

        console.log("xScale(d.x):", xScale(d.x));
        console.log("yScale(d.y):", yScale(d.y));
        g.append("text")
            .attr('class', 'val')
            .attr('x', function() {
                return xScale(d.x) + xScale.bandwidth() / 2; // Center the text within the bar
            })
            .attr('y', function() {
                return yScale(d.y) - 15;
            })
            .attr("text-anchor", "middle") // Center the text horizontally
            .text(function() {
                return d.y;
            });
    }

    //mouseout event handler function
    function onMouseOut(d, i) {
        // use the text label class to remove label on mouseout
        d3.select(this).attr('class', 'bar');
        d3.select(this)
            .transition()     // adds animation
            .duration(400)
            .attr('width', xScale.bandwidth())
            .attr("y", function(d) { return yScale(d.y); })
            .attr("height", function(d) { return height - yScale(d.y); });

        d3.selectAll('.val')
            .remove()
    }

}

// Function to create a line graph
function createLineChart(csvData) {
    // Assuming csvData is already defined
    const xAxis = document.getElementById("xAxis").value;
    const yAxis = document.getElementById("yAxis").value;

    // Use csvData to create the chart
    const data = csvData.map(row => ({
        x: +row[xAxis],
        y: +row[yAxis]

    }));

    var graphContainer = d3.select(".graph");

    var margin = { top: 10, right: 10, bottom: 20, left: 40 },
        width = graphContainer.node().getBoundingClientRect().width - margin.left - margin.right,
        height = graphContainer.node().getBoundingClientRect().height - margin.top - margin.bottom;

    var svg = graphContainer.append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    svg.append("text")
        .attr("transform", "translate(100,0)")
        .attr("x", 50)
        .attr("y", 50)
        .attr("font-size", "24px")
        .text(yAxis + " over " + xAxis);

    var xScale = d3.scaleLinear().range([0, width]),
        yScale = d3.scaleLinear().range([height, 0]);

    var line = d3.line()
        .x(function(d) { return xScale(d.x); })
        .y(function(d) { return yScale(d.y); });

    xScale.domain(d3.extent(data, function(d) { return d.x; }));
    yScale.domain(d3.extent(data, function(d) { return d.y; }));

    svg.append("path")
        .data([data])
        //.attr("class", "line")
        //.attr("d", line);

    // Add circles for each data point
    svg.selectAll("circle")
        .data(data)
        .enter().append("circle")
        .on("mouseover", onMouseOver) //Add listener for the mouseover event
        .on("mouseout", onMouseOut)   //Add listener for the mouseout event
        .attr("cx", function(d) { return xScale(d.x); })
        .attr("cy", function(d) { return yScale(d.y); })
        .attr("r", 5);

    svg.append("g")
        .attr("transform", "translate(0," + height + ")")
        .call(d3.axisBottom(xScale))
        .append("text")
        .attr("y", height - 300)
        .attr("x", width - 100)
        .attr("text-anchor", "end")
        .attr("stroke", "black")
        .text(xAxis);

    svg.append("g")
        .call(d3.axisLeft(yScale).tickFormat(function(d) {
            return d;
        }).ticks(10))
        .append("text")
        .attr("transform", "rotate(-90)")
        .attr("y", 6)
        .attr("dy", "-5.1em")
        .attr("text-anchor", "end")
        .attr("stroke", "black")
        .text(yAxis);

    function onMouseOver(d, i) {
        d3.select(this).attr('class', 'highlight');
        d3.select(this)
            .transition()
            .duration(400)
            .attr('width', xScale.bandwidth() + 5)
            .attr("y", function(d) { return yScale(d.y) - 10; })
            .attr("height", function(d) { return height - yScale(d.y) + 10; });

        g.append("text")
            .attr('class', 'val')
            .attr('x', function() {
                return xScale(d.x) + xScale.bandwidth() / 2; // Center the text within the bar
            })
            .attr('y', function() {
                return yScale(d.y) - 15;
            })
            .attr("text-anchor", "middle") // Center the text horizontally
            .text(function() {
                return d.y;
            });
    }

    //mouseout event handler function
    function onMouseOut(d, i) {
        // remove label on mouseout
        d3.select(this).attr('class', 'bar');
        d3.select(this)
            .transition()     // adds animation
            .duration(400)
            .attr('width', xScale.bandwidth())
            .attr("y", function(d) { return yScale(d.y); })
            .attr("height", function(d) { return height - yScale(d.y); });

        d3.selectAll('.val')
            .remove()
    }
}

// Function to create a pie chart
function createPieChart() {


    const data = [30, 20, 50];

    const width = 400;
    const height = 400;
    const radius = Math.min(width, height) / 2;

    const svg = d3.select("#chartContainer").append("svg")
        .attr("width", width)
        .attr("height", height)
        .append("g")
        .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

    const color = d3.scaleOrdinal(d3.schemeCategory10);

    const pie = d3.pie();

    const arc = d3.arc()
        .innerRadius(0)
        .outerRadius(radius);

    svg.selectAll("path")
        .data(pie(data))
        .enter()
        .append("path")
        .attr("d", arc)
        .attr("fill", (d, i) => color(i));

}

// Function to trigger the visualization based on chart type
function visualize() {
    const selectedChart = document.getElementById("chartType").value;

    // Clear existing chart
    d3.select("#chartContainer").html("");


    // Choose and create the selected chart type dynamically
    switch (selectedChart) {
        case "bar":
            createBarChart(csvData);
            break;
        case "line":
            createLineChart(csvData);
            break;
        case "pie":
            createPieChart();
            break;
        default:
            console.error("Invalid chart type selected");
    }
}
