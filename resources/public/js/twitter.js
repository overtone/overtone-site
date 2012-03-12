new TWTR.Widget({
  version: 2,
  type: 'profile',
  rpp: 4,
  interval: 30000,
  width: 270,
  height: 300,
  theme: {
    shell: {
      background: 'gray',
      color: '#ffffff'
    },
    tweets: {
      background: 'white',
      color: 'gray',
      links: 'blue'
    }
  },
  features: {
    scrollbar: true,
    loop: true,
    live: true,
    behavior: 'all'
  }
}).render().setUser('overtone').start();
