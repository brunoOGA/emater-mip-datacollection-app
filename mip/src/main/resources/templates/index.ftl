<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Sistema de Coleta e Análise MID/MIP</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

    <!-- Material icons -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>

<body>

    <main class="container-fluid">

        <!-- Add Menu -->
        <#include "/menubar.ftl">

        <h1>Painel de Acompanhamento</h1>
        <hr />

        <section id="top-panel-section" class="row" style="margin-left: 0.2rem; margin-top: 1rem">

            <div class="card col border-primary" style="padding: 0rem; margin-right: 1rem">
                <div class="card-body bg-primary text-white flex-row text-center">
                    <span>
                        <i class="material-icons" style="font-size: 48px">
                            home
                        </i>
                    </span>
                    <span class="float-right">
                        <h2 class="card-title">5</h5>
                            <p class="card-text">URs Cadastradas</p>
                    </span>
                </div>
                <div class="card-footer text-primary">
                    <a href="#">Criar nova UR</a>
                </div>
            </div>

            <div class="card col border-success" style="padding: 0rem; margin-right: 1rem">
                <div class="card-body bg-success text-white flex-row text-center">
                    <span>
                        <i class="material-icons" style="font-size: 48px">
                            assignment
                        </i>
                    </span>
                    <span class="float-right">
                        <h2 class="card-title">5</h5>
                            <p class="card-text">URs na Pesquisa Atual</p>
                    </span>
                </div>
                <div class="card-footer text-success">
                    <a href="#" class="text-success">Adicionar UR à Pesquisa</a>
                </div>
            </div>

            <div class="card col border-warning" style="padding: 0rem; margin-right: 1rem">
                <div class="card-body bg-warning text-white flex-row text-center">
                    <span>
                        <i class="material-icons" style="font-size: 48px">
                            list
                        </i>
                    </span>
                    <span class="float-right">
                        <h2 class="card-title">25</h5>
                            <p class="card-text">Amostras coletadas</p>
                    </span>
                </div>
                <div class="card-footer text-warning">
                    <a href="#" class="text-warning">Coletar amostra</a>
                </div>
            </div>


            <div class="card col border-danger" style="padding: 0rem; margin-right: 1rem">
                <div class="card-body bg-danger text-white flex-row text-center">
                    <span>
                        <i class="material-icons" style="font-size: 48px">
                            bug_report
                        </i>
                    </span>
                    <span class="float-right">
                        <h2 class="card-title">7</h5>
                        <p class="card-text">Pragas registradas</p>
                    </span>
                </div>
                <div class="card-footer text-danger">
                    <a href="#" class="text-danger">Observar evolução das pragas</a>
                </div>
            </div>

        </section>

        <section id="central-panel-section" class="row" style="margin-top: 1rem">

            <article id="right-centered-panel" class="col-sm-8">
                
                <div class="card" style="margin-top: 1rem">
                    <div class="card-header">
                        Flutuação das Pragas
                    </div>
                    <div id="pie-chart"></div>
                </div>       

                <div class="card" style="margin-top: 1rem">
                    <div class="card-header">
                        Evolução das Pragas
                    </div>
                    <div id="line-chart"></div>
                </div>                

            </article>

            <article id="left-panel" class="col-sm-4">

                <div class="card" style="margin-top: 1rem">
                    <div class="card-header">
                        Últimas amostras coletadas
                    </div>
                    <div class="card" style="margin: 1rem">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">Cras justo odio</li>
                            <li class="list-group-item">Dapibus ac facilisis in</li>
                            <li class="list-group-item">Vestibulum at eros</li>
                        </ul>

                    </div>
                </div>

                <div class="card" style="margin-top: 1rem">
                    <div class="card-header">
                        Incidência de Pragas/Município
                    </div>

                    <div id="bar-chart"></div>
                </div>

            </article>

        </section>
    </main>

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

    <!-- Plotly.js -->
    <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>

    <!-- Create plots -->
    <script>
        var data = [{
            values: [19, 26, 55],
            labels: ['Lagarta da soja', 'Falsa medideira', 'Lagarta das vagens'],
            type: 'pie'
        }];

        Plotly.newPlot('pie-chart', data);
    </script>

    <script>
        var trace1 = {
        x: [1, 2, 3, 4],
        y: [10, 15, 13, 17],
        mode: 'markers',
        name: 'Lagarta da soja'
        };

        var trace2 = {
        x: [2, 3, 4, 5],
        y: [16, 5, 11, 10],
        mode: 'lines',
        name: 'Falsa medideira'
        };

        var trace3 = {
        x: [1, 2, 3, 4],
        y: [12, 9, 15, 12],
        mode: 'lines+markers',
        name: 'Lagarta das vagens'
        };

        var data = [ trace1, trace2, trace3 ];

        var layout = {};

        Plotly.newPlot('line-chart', data, layout);    
    </script>

    <script>
        var trace1 = {
        x: ['Pato Branco', 'Apucarana', 'Londrina'], 
        y: [20, 14, 23], 
        name: 'Lagarta da soja', 
        type: 'bar'
        };

        var trace2 = {
        x: ['Pato Branco', 'Apucarana', 'Londrina'], 
        y: [12, 18, 29], 
        name: 'Falsa medideira', 
        type: 'bar'
        };

        var data = [trace1, trace2];
        var layout = {barmode: 'group'};

        Plotly.newPlot('bar-chart', data, layout);    
    </script>
</body>

</html>