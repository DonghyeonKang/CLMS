import React from 'react';
import Chart from 'react-apexcharts';

// props: dataAvg(number), dataLabel(string)
const DiskChart = (props) => {
    const { dataAvg, dataLabel } = props;

    const options = {
        chart: {
            height: 350,
            type: 'radialBar'
        },
        colors: ["#20E647"],
        plotOptions: {
            radialBar: {
                hollow: {
                    margin: 0,
                    size: '70%', // bar 굵기
                    background: "#192a56",
                },
                dataLabels: {
                    showOn: 'always',
                    name: {
                        offsetY: -10,
                        show: true,
                        color: '#fff',
                        fontSize: '20px'
                    },
                    value: {
                        color: '#fff',
                        fontSize: '36px',
                        show: true
                    }
                },
                track: {
                    dropShadow: {
                      enabled: true,
                      top: 2,
                      left: 0,
                      blur: 4,
                      opacity: 0.15
                    },
            },
        },
    },
    fill: {
        type: "gradient",
        gradient: {
          shade: "dark",
          type: "vertical",
          gradientToColors: ["#87D4F9"],
          stops: [0, 100]
        }
      },
      stroke: {
        lineCap: "round"
      },
    labels: [dataLabel],
};
    const series = [dataAvg];

    return (
        <div className="radialbar">
            <Chart options={options} series={series} type="radialBar" height="350" />
        </div>
    );
};

export default DiskChart;