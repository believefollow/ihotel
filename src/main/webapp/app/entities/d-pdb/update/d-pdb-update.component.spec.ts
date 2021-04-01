jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DPdbService } from '../service/d-pdb.service';
import { IDPdb, DPdb } from '../d-pdb.model';

import { DPdbUpdateComponent } from './d-pdb-update.component';

describe('Component Tests', () => {
  describe('DPdb Management Update Component', () => {
    let comp: DPdbUpdateComponent;
    let fixture: ComponentFixture<DPdbUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dPdbService: DPdbService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DPdbUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DPdbUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DPdbUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dPdbService = TestBed.inject(DPdbService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dPdb: IDPdb = { id: 456 };

        activatedRoute.data = of({ dPdb });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dPdb));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dPdb = { id: 123 };
        spyOn(dPdbService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dPdb });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dPdb }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dPdbService.update).toHaveBeenCalledWith(dPdb);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dPdb = new DPdb();
        spyOn(dPdbService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dPdb });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dPdb }));
        saveSubject.complete();

        // THEN
        expect(dPdbService.create).toHaveBeenCalledWith(dPdb);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dPdb = { id: 123 };
        spyOn(dPdbService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dPdb });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dPdbService.update).toHaveBeenCalledWith(dPdb);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
