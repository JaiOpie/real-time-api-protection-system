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

<ul>
  <li v-for="key in blockedKeys" :key="key">
    {{ key }}
  </li>
</ul>

<h2>API Traffic Trend</h2>

<TrafficChart :requests="requests"/>

    <h2>Recent API Traffic</h2>

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
</template>

<script>
import { getRequests, getBlockedKeys } from "../services/api"
import TrafficChart from "@/components/TrafficChart.vue";



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
      this.requests = req.data

      const blocked = await getBlockedKeys()
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
  font-family:Arial;
}

.stats{
  display:flex;
  gap:20px;
  margin-bottom:30px;
}

.card{
  background:#f3f4f6;
  padding:20px;
  border-radius:8px;
  width:200px;
  text-align:center;
}

table{
  width:100%;
  border-collapse:collapse;
}

th,td{
  border:1px solid #ddd;
  padding:8px;
}

th{
  background:#f0f0f0;
}

</style>