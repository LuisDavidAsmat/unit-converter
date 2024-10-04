const formatActiveTab = () =>
{
    const path = window.location.pathname;

    const resetStyles = () =>
    {
        const elements =
        [
            { link: 'length-link', svg: 'length-svg', title: 'length-title' },
            { link: 'temperature-link', svg: 'temp-svg', title: 'temp-title' },
            { link: 'weight-link', svg: 'weight-svg', title: 'weight-title' }
        ];

        elements.forEach(el =>
        {
            const link = document.getElementById(el.link);
            const svg = document.getElementById(el.svg);
            const title = document.getElementById(el.title);

            if (link) {
                link.classList.remove('bg-amber-600', 'shadow-md');
            }
            if (svg)
            {
                svg.style.stroke = "#000";
                svg.style.fill = "";
            }
            if (title) {
                title.style.color = "";
                title.style.textDecoration = "";
            }
        });
    };

    const applyStyles = (linkId, svgId, titleId) =>
    {
        const link = document.getElementById(linkId);
        const svg = document.getElementById(svgId);
        const title = document.getElementById(titleId);

        if (link)
        {
            link.classList.add('bg-amber-600', 'shadow-md');
        }

        if (svg)
        {
            svg.style.stroke = "#fff";

            if (svgId !== 'length-svg')
            {
                svg.style.fill = "#fff";
            }
        }
        if (title)
        {
            title.style.color = "#fff";
            title.style.textDecoration = "underline";
        }
    };

    resetStyles();

    if (path === "/length-conversion" || path === "/")
    {
        applyStyles('length-link', 'length-svg', 'length-title');
    }
    else if (path === "/temperature-conversion")
    {
        applyStyles('temperature-link', 'temp-svg', 'temp-title');
    }
    else if (path === "/weight-conversion")
    {
        applyStyles('weight-link', 'weight-svg', 'weight-title');
    }
};

document.addEventListener("DOMContentLoaded", formatActiveTab);