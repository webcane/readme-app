import svelte from 'rollup-plugin-svelte';
import resolve from '@rollup/plugin-node-resolve';
import commonjs from '@rollup/plugin-commonjs';
import livereload from 'rollup-plugin-livereload';
import { terser } from 'rollup-plugin-terser';
import filesize from 'rollup-plugin-filesize';
import css from 'rollup-plugin-css-only';
import copy from 'rollup-plugin-copy';

//const production = !process.env.ROLLUP_WATCH;
const mode = process.env.NODE_ENV || 'development';
const production = mode === 'production';

export default {
	input: 'src/main/svelte/main.js',
	output: {
		sourcemap: true,
		format: 'iife',
		name: 'readmeApp',
		file: 'src/main/resources/public/build/bundle.js'
	},
	plugins: [
		// copy bootstrap-social.css, font-awesome.css into extra.css
		// css({
		// 	//output: "./src/main/resources/public/build/extra.css"
		// 	output: function (styles, styleNodes) {
		// 		// Filter out any source map imports
		// 		let reg = new RegExp(/^(\/*).*(.map) ?(\*\/)$/gm);
		// 		writeFileSync("./src/main/resources/public/build/extra.css", styles.replace(reg, ""))
		// 	}
		// }),

		svelte({
			// enable run-time checks when not in production
			dev: !production,
			// we'll extract any component CSS out into
			// a separate file - better for performance
			css: css => {
				css.write('bundle.css', !production);
			},
		}),

		// copy font-awesome into fonts folder
		copy({ 
			targets: [{ 
				src: ['./node_modules/font-awesome/fonts/fontawesome-webfont.eot',
					  './node_modules/font-awesome/fonts/fontawesome-webfont.svg',
					  './node_modules/font-awesome/fonts/fontawesome-webfont.ttf',
					  './node_modules/font-awesome/fonts/fontawesome-webfont.woff',
					  './node_modules/font-awesome/fonts/fontawesome-webfont.woff2'], 
				dest: './src/main/resources/public/fonts' 
			}],
			copyOnce: true
		}),

		// If you have external dependencies installed from
		// npm, you'll most likely need these plugins. In
		// some cases you'll need additional configuration -
		// consult the documentation for details:
		// https://github.com/rollup/plugins/tree/master/packages/commonjs
		resolve({
			browser: true,
			dedupe: ['svelte']
		}),

		commonjs(),

		// In dev mode, call `npm run start` once
		// the bundle has been generated
		!production && serve(),

		// Watch the `public` directory and refresh the
		// browser on changes when not in production
		!production && livereload('src/main/resources/public'),

		// If we're building for production (npm run build
		// instead of npm run dev), minify
		production && terser(),
		production && filesize()
	],
	watch: {
		clearScreen: false
	}
};

function serve() {
	let started = false;

	return {
		writeBundle() {
			if (!started) {
				started = true;

				require('child_process').spawn('npm', ['run', 'start', '--', '--dev'], {
					stdio: ['ignore', 'inherit', 'inherit'],
					shell: true
				});
			}
		}
	};
}
