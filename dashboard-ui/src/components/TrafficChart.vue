<template>
  <div class="chart-container">
    <canvas ref="chart"></canvas>
  </div>
</template>

<script>
import { Chart } from "chart.js/auto"

export default {

  props: ["requests"],

  data() {
    return {
      chart: null
    }
  },

mounted() {
  if (this.requests.length > 0) {
    this.updateChart()
  }
},

  watch: {
    requests() {
      this.updateChart()
    }
  },

  methods: {

    groupRequests() {

      const counts = {}

      this.requests.forEach(r => {

        const time = new Date(r.timestamp)
          .toLocaleTimeString()

        counts[time] = (counts[time] || 0) + 1
      })

      return counts
    },

    renderChart() {

      const ctx = this.$refs.chart

      const grouped = this.groupRequests()

this.chart = new Chart(ctx, {
  type: "line",

  options:{
    responsive:true,
    maintainAspectRatio:false
  },

  data:{
          labels: Object.keys(grouped),

          datasets: [
            {
              label: "Requests",
              data: Object.values(grouped),
              borderColor: "blue",
              tension: 0.3
            }
          ]
        }

      })
    },

updateChart() {

  if (!this.$refs.chart) return

  const grouped = this.groupRequests()

  if (this.chart) {
    this.chart.destroy()
  }

  const ctx = this.$refs.chart

  this.chart = new Chart(ctx, {
    type: "line",
    data: {
      labels: Object.keys(grouped),
      datasets: [
        {
          label: "Requests",
          data: Object.values(grouped),
          borderColor: "blue",
          tension: 0.3
        }
      ]
    }
  })
}

  }

}
</script>

<style scoped>

.chart-container{
  width:100%;
  height:350px;
  position:relative;
}

canvas{
  width:100% !important;
  height:100% !important;
}

</style>