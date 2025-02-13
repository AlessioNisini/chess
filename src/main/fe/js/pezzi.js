// pezzi.js
const WP_svg = `
<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:serif="http://www.serif.com/" width="100%" height="100%" viewBox="0 0 800 800" version="1.1" xml:space="preserve" style="fill-rule:evenodd;clip-rule:evenodd;stroke-linejoin:round;stroke-miterlimit:2;">
    <g>
        <g>
            <path d="M242.23,596.998C244.966,597.044 341.923,596.998 392.419,596.998C442.729,596.998 562.731,597.044 565.467,596.998C570.104,596.812 581.371,587.909 578.357,576.781C575.019,562.87 562.685,559.856 558.187,559.856L249.417,559.856C244.966,559.856 232.586,562.87 229.201,576.781C226.326,587.863 237.593,596.812 242.23,596.998Z" style="fill:white;fill-rule:nonzero;stroke:black;stroke-width:10px;"/>
            <path d="M523.735,553.04C494.755,493.178 457.289,324.674 446.531,254.147L479.175,254.147C488.448,254.147 496.053,245.847 496.053,235.553C496.053,225.306 488.448,217.006 479.175,217.006L439.715,217.006L439.622,216.171C473.935,197.855 497.676,158.72 497.676,113.603C497.676,50.82 451.863,0 395.294,0C338.724,0 292.958,50.867 292.958,113.603C292.958,158.767 316.606,197.855 350.919,216.171L350.872,217.006L311.366,217.006C302,217.006 294.488,225.306 294.488,235.553C294.488,245.847 302,254.147 311.366,254.147L344.01,254.147C333.438,324.349 302.602,484.322 276.172,553.04L523.735,553.04Z" style="fill:white;fill-rule:nonzero;stroke:black;stroke-width:10px;"/>
            <g>
                <path d="M592.964,769.258C589.393,759.335 576.92,748.299 576.92,748.299L223.034,748.299C223.034,748.299 210.653,758.036 206.48,768.562C202.307,779.088 202.214,800 202.214,800L597.786,800C597.786,799.954 596.58,779.227 592.964,769.258Z" style="fill:white;fill-rule:nonzero;stroke:black;stroke-width:10px;"/>
                <path d="M556.193,608.08L248.258,608.08L225.816,740.37L574.138,740.37L556.193,608.08Z" style="fill:white;fill-rule:nonzero;stroke:black;stroke-width:10px;"/>
            </g>
        </g>
    </g>
</svg>
`;

const BP_svg = `
<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:serif="http://www.serif.com/" width="100%" height="100%" viewBox="0 0 800 800" version="1.1" xml:space="preserve" style="fill-rule:evenodd;clip-rule:evenodd;stroke-linejoin:round;stroke-miterlimit:2;">
    <g>
        <g>
            <path d="M242.23,596.998C244.966,597.044 341.923,596.998 392.419,596.998C442.729,596.998 562.731,597.044 565.467,596.998C570.104,596.812 581.371,587.909 578.357,576.781C575.019,562.87 562.685,559.856 558.187,559.856L249.417,559.856C244.966,559.856 232.586,562.87 229.201,576.781C226.326,587.863 237.593,596.812 242.23,596.998Z" style="fill:black;fill-rule:nonzero;stroke:black;stroke-width:2px;"/>
            <path d="M523.735,553.04C494.755,493.178 457.289,324.674 446.531,254.147L479.175,254.147C488.448,254.147 496.053,245.847 496.053,235.553C496.053,225.306 488.448,217.006 479.175,217.006L439.715,217.006L439.622,216.171C473.935,197.855 497.676,158.72 497.676,113.603C497.676,50.82 451.863,0 395.294,0C338.724,0 292.958,50.867 292.958,113.603C292.958,158.767 316.606,197.855 350.919,216.171L350.872,217.006L311.366,217.006C302,217.006 294.488,225.306 294.488,235.553C294.488,245.847 302,254.147 311.366,254.147L344.01,254.147C333.438,324.349 302.602,484.322 276.172,553.04L523.735,553.04Z" style="fill:black;fill-rule:nonzero;stroke:black;stroke-width:2px;"/>
            <g>
                <path d="M592.964,769.258C589.393,759.335 576.92,748.299 576.92,748.299L223.034,748.299C223.034,748.299 210.653,758.036 206.48,768.562C202.307,779.088 202.214,800 202.214,800L597.786,800C597.786,799.954 596.58,779.227 592.964,769.258Z" style="fill:black;fill-rule:nonzero;stroke:black;stroke-width:2px;"/>
                <path d="M556.193,608.08L248.258,608.08L225.816,740.37L574.138,740.37L556.193,608.08Z" style="fill:black;fill-rule:nonzero;stroke:black;stroke-width:2px;"/>
            </g>
        </g>
    </g>
</svg>
`;


// Funzione per creare un elemento SVG con le dimensioni desiderate
function creaPezzoSVG(svgCode, larghezza, altezza) {
  const div = document.createElement('div');
  div.innerHTML = svgCode;
  const svgElement = div.querySelector('svg');
  svgElement.setAttribute('width', larghezza);
  svgElement.setAttribute('height', altezza);
  return svgElement;
}