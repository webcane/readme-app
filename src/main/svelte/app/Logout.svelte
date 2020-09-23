<script>
    import {tweened} from 'svelte/motion';
    import {createEventDispatcher} from 'svelte';

    const dispatch = createEventDispatcher();

    let original = 3;
    let timer = tweened(original)

    const interval = setInterval(() => {
        console.log($timer);
        if ($timer > 0) {
            $timer--;
        } else if ($timer == 0) {
            clearInterval(interval);
            dispatch('menu', '');
        }
    }, 1000);

</script>


<svelte:head>
    <title>Log out</title>
</svelte:head>

<div class="auth-page">
    <div class="container page">
        <div class="row">
            <div class="col-md-6 offset-md-3 col-xs-12">
                You have just logged out. Want to come back?
            </div>
        </div>
    </div>
</div>