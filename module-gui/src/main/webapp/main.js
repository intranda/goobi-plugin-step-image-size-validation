import * as riot from 'riot';
import App from './tags/app.riot';

import './resources/css/style.css';

const mountApp = riot.component(App);

const rootElement = document.getElementById("root");

if (!rootElement) {
    console.error("[main.js] ERROR: Could not find element with id 'root'!");
} else {
    const app = mountApp(rootElement);
}