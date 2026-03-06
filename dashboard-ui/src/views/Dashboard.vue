<template>
  <div class="dashboard">

    <h1>API Monitoring Dashboard</h1>

    <div class="stats">

      <div class="card">
        <h3>Total Requests</h3>
        <p>{{ requests.length }}</p>
      </div>

      <div class="card">
        <h3>Blocked Keys</h3>
        <p>{{ blockedCount }}</p>
      </div>

      <div class="card">
        <h3>Avg Response Time</h3>
        <p>{{ avgResponseTime }} ms</p>
      </div>

    </div>

    <h2>Blocked API Keys</h2>

<div class="card" v-for="key in blockedKeys" :key="key">
  🚫 {{ key }}
</div>

<h2>API Traffic Trend</h2>

<div class="chart-card">
  <TrafficChart :requests="requests"/>
</div>

    <h2>Recent API Traffic</h2>

<div class="table-container">
    <table>
      <thead>
        <tr>
          <th>API Key</th>
          <th>Path</th>
          <th>Method</th>
          <th>Status</th>
          <th>Response Time</th>
          <th>Time</th>

        </tr>
      </thead>

      <tbody>
        <tr v-for="req in requests" :key="req.id">
          <td>{{ req.apiKey }}</td>
          <td>{{ req.path }}</td>
          <td>{{ req.method }}</td>
          <td :style="{ color: Number(req.status) === 429 ? 'red' : 'green', fontWeight: 'bold' }">
        {{ req.status }}
        </td>
          <td>{{ req.responseTime }}</td>
          <td>{{ new Date(req.timestamp).toLocaleTimeString() }}</td>
        </tr>
      </tbody>
    </table>
    </div>

  </div>
</template>

<script>
import { getRequests, getBlockedKeys } from "../services/api"
import TrafficChart from "../components/TrafficChart.vue";


export default {

  components : {
  TrafficChart
},

  data() {
    return {
      requests: [],
      blockedKeys: []
    }
  },

  methods: {

    async loadData() {

      const req = await getRequests()
        console.log("Requests:", req.data)

      this.requests = req.data

      const blocked = await getBlockedKeys()
        console.log("Requests:", req.data)

      this.blockedKeys = blocked.data

    }

  },

  computed: {

    blockedCount() {
      return this.requests.filter(r => Number(r.status) === 429).length
    },

    avgResponseTime() {

      if (this.requests.length === 0) return 0

      const total = this.requests.reduce(
        (sum, r) => sum + r.responseTime,
        0
      )

      return Math.round(total / this.requests.length)

    }

  },

  async mounted() {

    await this.loadData()

    setInterval(() => {
      this.loadData()
    }, 5000)

  }

}
</script>

<style>

.dashboard{
  padding:40px;
  font-family:Arial, Helvetica, sans-serif;
  background:#0f172a;
  min-height:100vh;
  color:white;
}

.stats{
  display:flex;
  gap:20px;
  margin-bottom:40px;
}

.chart-card{
  background:#1e293b;
  padding:25px;
  border-radius:12px;
  width:100%;
  margin-bottom:30px;
  box-shadow:0px 4px 12px rgba(0,0,0,0.4);
}

.card{
  background:#1e293b;
  padding:20px;
  border-radius:10px;
  width:220px;
  text-align:center;
  box-shadow:0px 4px 10px rgba(0,0,0,0.4);
}

.card h3{
  margin-bottom:10px;
}

.card p{
  font-size:28px;
  font-weight:bold;
}

table{
  width:100%;
  border-collapse:collapse;
  background:#1e293b;
  border-radius:8px;
  overflow:hidden;
}

th,td{
  padding:10px;
  text-align:left;
}

th{
  background:#334155;
}

tr:nth-child(even){
  background:#1e293b;
}

tr:nth-child(odd){
  background:#172036;
}

h1{
  margin-bottom:30px;
}

h2{
  margin-top:30px;
  margin-bottom:15px;
}

.table-container{
  max-height:300px;
  overflow-y:auto;
  border-radius:8px;
}

thead th{
  position:sticky;
  top:0;
  background:#334155;
  z-index:1;
}

</style>