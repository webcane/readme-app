// Components
import Articles from './Articles.svelte'
import NotFound from './NotFound.svelte'

let routes = new Map()

routes.set('/', Articles)
routes.set('/tags/:tagName', Articles)

// Catch-all, must be last
routes.set('*', NotFound)

export default routes