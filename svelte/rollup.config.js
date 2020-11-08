import svelte from 'rollup-plugin-svelte';
import resolve from '@rollup/plugin-node-resolve';
import commonjs from '@rollup/plugin-commonjs';
import livereload from 'rollup-plugin-livereload';
import { terser } from 'rollup-plugin-terser';
import filesize from 'rollup-plugin-filesize';
import css from 'rollup-plugin-css-only';
import copy from 'rollup-plugin-copy';

const production = !process.env.ROLLUP_WATCH;

export default {
	input: 'src/main.js',
	output: {
		sourcemap: true,
		format: 'iife',
		name: 'readme',
		file: 'public/build/bundle.js'
	},
	plugins: [
		svelte({
			// enable run-time checks when not in production
			dev: !production,
			// we'll extract any component CSS out into
			// a separate file - better for performance
			css: css => {
				css.write('bundle.css',true);
			}
		}),

		// copy bootstrap-social.css, font-awesome.css into extra.css
		css({ output: "public/build/extra.css" }),

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
		!production && livereload('public'),

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
