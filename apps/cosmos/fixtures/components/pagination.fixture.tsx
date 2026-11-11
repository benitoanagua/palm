import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [page, setPage] = useFixtureInput('page', 1);
  const [pageCount, setPageCount] = useFixtureInput('pageCount', 10);
  const [siblingCount, setSiblingCount] = useFixtureInput('siblingCount', 1);
  return (
    <pm-pagination
      page={page}
      pageCount={pageCount}
      siblingCount={siblingCount}
      label="Paginación"
      onPmChange={(e) => setPage(e.detail)}
    />
  );
};

export const Default = () => <pm-pagination page="1" pageCount="5" label="Paginación" />;
export const LastPage = () => <pm-pagination page="10" pageCount="20" label="Paginación" />;

export default { Playground, Default, LastPage };
